package helpers;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A binary input stream that wraps an arbitrary input stream and allows reading from it bitwise,
 * which is useful for compression algorithms that operate on the bit level. Bits are consumed from
 * each byte most-significant-bit first, and multi-byte values are read big-endian. The counterpart
 * for writing is {@link helpers.BinaryOutputStream}; the exact encoding is specified in
 * {@code docs/binary-streams.md}.
 */
public final class BinaryInputStream {
  private static final int EOF = -1;

  private final InputStream in;
  private int current;  // the byte currently being consumed, or EOF once the stream is exhausted
  private int bitsLeft; // unconsumed bits remaining in the current byte, always in [0, 8]

  /**
   * Create a new binary input stream wrapped around the given input stream. The first byte is
   * read eagerly so that {@link #isEmpty()} is accurate from the start.
   * @param aIn the input stream to read from
   * @throws IOException if an error occurs while reading the first byte
   */
  public BinaryInputStream(InputStream aIn) throws IOException {
    in = aIn;
    refill();
  }

  private void refill() throws IOException {
    current = in.read();
    bitsLeft = current == EOF ? 0 : 8;
  }

  /**
   * @return true, iff every bit of the stream has been consumed; performs no I/O
   */
  public boolean isEmpty() {
    return current == EOF;
  }

  /**
   * @return the next bit from the stream, {@code true} = 1
   * @throws EOFException if the stream is empty
   * @throws IOException if an error occurs during I/O
   */
  public boolean readBit() throws IOException {
    if ( isEmpty() ) { throw new EOFException("Reading past the end of the stream"); }
    bitsLeft--;
    boolean bit = ( ( current >>> bitsLeft ) & 1 ) == 1;
    if ( bitsLeft == 0 ) { refill(); }
    return bit;
  }

  /**
   * Read the next 8 bits as a character. To read 16 bits, use {@code readChar(16)}.
   * @return the next 8 bits of data from the stream as a {@code char}
   * @throws EOFException if fewer than 8 bits remain
   * @throws IOException if an error occurs during I/O
   */
  public char readChar() throws IOException {
    return readChar(8);
  }

  /**
   * Read the next n bits as a character, first bit read = most significant of the n bits.
   * @param n the number of bits that shall be read
   * @return the next n bits of data as a {@code char}
   * @throws EOFException if fewer than n bits remain
   * @throws IOException if an error occurs during I/O
   * @throws IllegalArgumentException if not {@literal 1 <= n <= 16}
   */
  public char readChar(int n) throws IOException {
    if ( n < 1 || n > 16 ) { throw new IllegalArgumentException("Illegal value of n = " + n); }
    return (char)readBits(n);
  }

  /**
   * Read the next 32 bits as an int, first bit read = most significant bit.
   * @return the next 32 bits from the stream interpreted as an {@code int}
   * @throws EOFException if fewer than 32 bits remain
   * @throws IOException if an error occurs during I/O
   */
  public int readInt() throws IOException {
    return readBits(32);
  }

  private int readBits(int n) throws IOException {
    int x = 0;
    for ( int i = 0; i < n; i++ ) {
      x = ( x << 1 ) | ( readBit() ? 1 : 0 );
    }
    return x;
  }

  /**
   * Close the underlying stream.
   * @throws IOException if an error occurs while closing the underlying stream
   */
  public void close() throws IOException {
    in.close();
  }
}
