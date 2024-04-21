package org.avidd.util.io;

import java.io.IOException;
import java.io.OutputStream;


/**
 * A binary output stream that can be wrapped around any output stream and allows to write to the 
 * output bitwise. This is useful, for instance, for compression algorithms that operate on the 
 * bit-level.
 * When this stream writes Java primitive types it uses a big-endian order of bits, that is, the 
 * most significant byte is written first. This output stream will usually be accompanied by a 
 * {@link org.avidd.util.io.BinaryInputStream}.
 */
public final class BinaryOutputStream {
  private int b; 
  private int buffer; 
  private final OutputStream out;

  /**
   * Create a new binary output stream writing to the given output stream.
   * @param aOut the stream to write to
   */
  public BinaryOutputStream(OutputStream aOut) {
    out = aOut;
  }

  /**
   * @param bit the bit to be written
   * @throws IOException if an error occurs during I/O
   */
  private void writeBit(boolean bit) throws IOException {
    // add bit to buffer
    buffer <<= 1;
    if ( bit )
      buffer |= 1;

    b++;

    // if buffer is full (8 bits), write out as a single byte
    if ( b == 8 )
      flushBuffer();
  }

  /**
   * Writes the given 8-bit int to the output
   * @param write the least significant 8 bits of the given {@code int} to the output
   * @throws IOException if an error occurs during I/O
   * @throws IllegalArgumentException if the input is not in the range {@literal [0, 255]}
   */
  private void writeByte(int value) throws IOException, IllegalArgumentException {
    if ( value < 0 || value >= 256 ) { 
      throw new IllegalArgumentException("the input is not in [0, 255]"); 
    }

    if ( b == 0 ) {
      out.write(value);
      return;
    }

    for ( int i = 0; i < 8; i++ ) {
      boolean bit = ( ( value >>> ( 8 - i - 1 ) ) & 1 ) == 1;
      writeBit(bit);
    }
  }

  // TODO now write out any remaining bits in buffer to standard output, padding with 0s
  private void flushBuffer() throws IOException {
    if ( b == 0 ) { return; }
    if ( b > 0 ) { buffer <<= ( 8 - b ); } // shift so that the buffer content has the correct value
    out.write(buffer);
    // clear the buffer
    b = 0;
    buffer = 0;
  }

  /**
   * Flush the output stream and pad the least significant bits of the buffer with 0s.
   * 
   * @throws IOException if an error occurs during I/O
   */
  public void flush() throws IOException {
    flushBuffer();
    out.flush();
  }

  /**
   * Close the output stream but flush first. 
   * 
   * @throws IOException if an error occurs during I/O
   */
  public void close() throws IOException {
    flush();
    out.close();
  }

  /************************** BIS HIER ************************/
  
  /**
   * Write the specified bit to standard output.
   * 
   * @param bit the {@code boolean} or {@code bit} to be written
   * @throws IOException if an error occurs during I/O
   */
  public void write(boolean bit) throws IOException {
    writeBit(bit);
  }

  /**
   * Write the 8-bit byte to standard output.
   * 
   * @param x
   *          the <tt>byte</tt> to write.
   * @throws IOException
   */
  public void write(byte x) throws IOException {
    writeByte(x & 0xff);
  }

  /**
   * Write the 32-bit int to standard output.
   * 
   * @param x
   *          the <tt>int</tt> to write.
   * @throws IOException
   */
  public void write(int x) throws IOException {
    writeByte( ( x >>> 24 ) & 0xff); // write most significant byte
    writeByte( ( x >>> 16 ) & 0xff);
    writeByte( ( x >>> 8 ) & 0xff);
    writeByte( ( x >>> 0 ) & 0xff); // write least significant byte
  }

  /**
   * Write the r-bit int to standard output.
   * 
   * @param x
   *          the <tt>int</tt> to write.
   * @param r
   *          the number of relevant bits in the char.
   * @throws IOException
   * @throws RuntimeException
   *           if <tt>r</tt> is not between 1 and 32.
   * @throws RuntimeException
   *           if <tt>x</tt> is not between 0 and 2<sup>r</sup> - 1.
   */
  public void write(int x, int r) throws IOException {
    if ( r == 32 ) {
      write(x);
      return;
    }
    if ( r < 1 || r > 32 )
      throw new IllegalArgumentException("Illegal value for r = " + r);
    if ( x < 0 || x >= ( 1 << r ) )
      throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
    for ( int i = 0; i < r; i++ ) {
      boolean bit = ( ( x >>> ( r - i - 1 ) ) & 1 ) == 1;
      writeBit(bit);
    }
  }

  /**
   * Write the 64-bit double to standard output.
   * 
   * @param x
   *          the <tt>double</tt> to write.
   * @throws IOException
   */
  public void write(double x) throws IOException {
    write(Double.doubleToRawLongBits(x));
  }

  /**
   * Write the 64-bit long to standard output.
   * 
   * @param x
   *          the <tt>long</tt> to write.
   * @throws IOException
   */
  public void write(long x) throws IOException {
    writeByte((int) ( ( x >>> 56 ) & 0xff ));
    writeByte((int) ( ( x >>> 48 ) & 0xff ));
    writeByte((int) ( ( x >>> 40 ) & 0xff ));
    writeByte((int) ( ( x >>> 32 ) & 0xff ));
    writeByte((int) ( ( x >>> 24 ) & 0xff ));
    writeByte((int) ( ( x >>> 16 ) & 0xff ));
    writeByte((int) ( ( x >>> 8 ) & 0xff ));
    writeByte((int) ( ( x >>> 0 ) & 0xff ));
  }

  /**
   * Write the 32-bit float to standard output.
   * 
   * @param x
   *          the <tt>float</tt> to write.
   * @throws IOException
   */
  public void write(float x) throws IOException {
    write(Float.floatToRawIntBits(x));
  }

  /**
   * Write the 16-bit int to standard output.
   * 
   * @param x
   *          the <tt>short</tt> to write.
   * @throws IOException
   */
  public void write(short x) throws IOException {
    writeByte( ( x >>> 8 ) & 0xff);
    writeByte( ( x >>> 0 ) & 0xff);
  }

  /**
   * Write the 8-bit char to standard output.
   * 
   * @param x
   *          the <tt>char</tt> to write.
   * @throws IOException
   * @throws RuntimeException
   *           if <tt>x</tt> is not betwen 0 and 255.
   */
  public void write(char x) throws IOException {
    if ( x < 0 || x >= 256 )
      throw new RuntimeException("Illegal 8-bit char = " + x);
    writeByte(x);
  }

  /**
   * Write the r-bit char to standard output.
   * 
   * @param x
   *          the <tt>char</tt> to write.
   * @param r
   *          the number of relevant bits in the char.
   * @throws IOException
   * @throws RuntimeException
   *           if <tt>r</tt> is not between 1 and 16.
   * @throws RuntimeException
   *           if <tt>x</tt> is not between 0 and 2<sup>r</sup> - 1.
   */
  public void write(char x, int r) throws IOException {
    if ( r == 8 ) {
      write(x);
      return;
    }
    if ( r < 1 || r > 16 )
      throw new RuntimeException("Illegal value for r = " + r);
    if ( x < 0 || x >= ( 1 << r ) )
      throw new RuntimeException("Illegal " + r + "-bit char = " + x);
    for ( int i = 0; i < r; i++ ) {
      boolean bit = ( ( x >>> ( r - i - 1 ) ) & 1 ) == 1;
      writeBit(bit);
    }
  }

  /**
   * Write the string of 8-bit characters to standard output.
   * 
   * @param s
   *          the <tt>String</tt> to write.
   * @throws IOException
   * @throws RuntimeException
   *           if any character in the string is not between 0 and 255.
   */
  public void write(String s) throws IOException {
    for ( int i = 0; i < s.length(); i++ ) {
      write(s.charAt(i));
    }
  }

  /**
   * Write the String of r-bit characters to standard output.
   * 
   * @param s
   *          the <tt>String</tt> to write.
   * @param r
   *          the number of relevants bits in each character.
   * @throws IOException
   * @throws RuntimeException
   *           if r is not between 1 and 16.
   * @throws RuntimeException
   *           if any character in the string is not between 0 and 2<sup>r</sup>
   *           - 1.
   */
  public void write(String s, int r) throws IOException {
    for ( int i = 0; i < s.length(); i++ ) {
      write(s.charAt(i), r);
    }
  }
}
