package helpers;

import java.io.IOException;
import java.io.OutputStream;

/**
 * A binary output stream that wraps an arbitrary output stream and allows writing to it bitwise,
 * which is useful for compression algorithms that operate on the bit level. Bits fill each byte
 * most-significant-bit first, and multi-byte values are written big-endian. The counterpart for
 * reading is {@link helpers.BinaryInputStream}; the exact encoding is specified in
 * {@code docs/binary-streams.md}.
 */
public final class BinaryOutputStream {
  private final OutputStream out;
  private int buffer;   // bits accumulated so far, in the low-order end
  private int bitCount; // number of bits currently in the buffer, always in [0, 8)

  /**
   * Create a new binary output stream writing to the given output stream. Performs no I/O.
   * @param aOut the stream to write to
   */
  public BinaryOutputStream(OutputStream aOut) {
    out = aOut;
  }

  /**
   * Write a single bit.
   * @param bit the bit to be written, {@code true} = 1
   * @throws IOException if an error occurs during I/O
   */
  public void write(boolean bit) throws IOException {
    buffer = ( buffer << 1 ) | ( bit ? 1 : 0 );
    bitCount++;
    if ( bitCount == 8 ) {
      out.write(buffer);
      buffer = 0;
      bitCount = 0;
    }
  }

  /**
   * Write the given character as 8 bits.
   * @param x the character to write
   * @throws IOException if an error occurs during I/O
   * @throws IllegalArgumentException if {@code x} is not in the range {@literal [0, 255]}
   */
  public void write(char x) throws IOException {
    if ( x > 255 ) { throw new IllegalArgumentException("Character out of 8-bit range: " + (int)x); }
    write(x, 8);
  }

  /**
   * Write the r low-order bits of the given character, most significant of those bits first.
   * @param x the character to write
   * @param r the number of relevant bits in the character
   * @throws IOException if an error occurs during I/O
   * @throws IllegalArgumentException if not {@literal 1 <= r <= 16}, or if x does not fit in r bits
   */
  public void write(char x, int r) throws IOException {
    if ( r < 1 || r > 16 ) { throw new IllegalArgumentException("Illegal value of r = " + r); }
    if ( r < 16 && ( x >>> r ) != 0 ) {
      throw new IllegalArgumentException("Value " + (int)x + " does not fit in " + r + " bits");
    }
    writeBits(x, r);
  }

  /**
   * Write the given int as 32 bits, most significant bit first. All int values are legal,
   * including negative ones.
   * @param x the int to write
   * @throws IOException if an error occurs during I/O
   */
  public void write(int x) throws IOException {
    writeBits(x, 32);
  }

  /**
   * Write the r low-order bits of the given int, most significant of those bits first.
   * @param x the int to write
   * @param r the number of relevant bits in the int
   * @throws IOException if an error occurs during I/O
   * @throws IllegalArgumentException if not {@literal 1 <= r <= 32}, or if x does not fit in r bits
   */
  public void write(int x, int r) throws IOException {
    if ( r < 1 || r > 32 ) { throw new IllegalArgumentException("Illegal value of r = " + r); }
    if ( r < 32 && ( x < 0 || ( x >>> r ) != 0 ) ) {
      throw new IllegalArgumentException("Value " + x + " does not fit in " + r + " bits");
    }
    writeBits(x, r);
  }

  private void writeBits(int x, int r) throws IOException {
    for ( int i = r - 1; i >= 0; i-- ) {
      write(( ( x >>> i ) & 1 ) == 1);
    }
  }

  /**
   * Complete the current byte, if any, by padding its remaining low-order bits with 0s, write it,
   * and flush the underlying stream. Because the reader cannot tell padding bits from data bits,
   * this is only meaningful at the end of the stream or between byte-aligned sections.
   * @throws IOException if an error occurs during I/O
   */
  public void flush() throws IOException {
    if ( bitCount > 0 ) {
      out.write(buffer << ( 8 - bitCount ));
      buffer = 0;
      bitCount = 0;
    }
    out.flush();
  }

  /**
   * Flush, then close the underlying stream.
   * @throws IOException if an error occurs during I/O
   */
  public void close() throws IOException {
    flush();
    out.close();
  }
}
