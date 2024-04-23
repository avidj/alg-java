package helpers;

import java.io.InputStream;
import java.io.IOException;

/**
 * A binary input stream that can be wrapped around any input stream and allows to read from the 
 * input bitwise. This is useful, for instance, for compression algorithms that operate on the 
 * bit-level.
 * When this stream interprets bits as Java primitive types it assumes a big-endian order of bits,
 * that is, the most significant byte is assumed to be read first. This input stream will usually
 * be accompanied by a {@link helpers.BinaryOutputStream}.
 * 
 * Copyright 2000-2022, Robert Sedgewick and Kevin Wayne
 */
public final class BinaryInputStream {
  private static final int EOF = -1; // end of file

  // a buffer containing the next character (Java streams read ints)
  private int buffer; 
  // the number of bits not yet read from the buffer 
  private int b;
  // the input stream to read from
  private final InputStream in;

  /**
   * Create a new binary input stream that is wrapped around the given input stream.
   * @param aIn the input stream to read from
   * @throws IOException if an error occurs while reading the first byte
   */
  public BinaryInputStream(InputStream aIn) throws IOException {
    in = aIn;
    bufferNextInt();
  }

  /**
   * Read the next 8 bits into the buffer.
   * @throws IOException if an error occurs during I/O
   */
  private void bufferNextInt() throws IOException {
    buffer = in.read();
    b = 8;
  }

  /**
   * Close the underlying stream.
   * @throws IOException if an error occurs while closing the underlying stream
   */
  public void close() throws IOException {
    in.close();
  }

  /**
   * @return true, iff the end of the stream has been reached
   */
  public boolean isEmpty() {
    return buffer == EOF;
  }

  /**
   * @return the next bit from the stream interpreted as a {@code boolean}
   * @throws IOException if an error occurs during I/O
   */
  public boolean readBit() throws IOException {
    if ( isEmpty() ) {
      throw new IllegalStateException("Cannot read from empty stream.");
    }
    b--;
    boolean bit = ( ( buffer >> b ) & 1 ) == 1;
    if ( b == 0 )
      bufferNextInt();
    return bit;
  }

  /**
   * TODO: original
   * Read the next 8 bits from standard input and return as an 8-bit char. Note
   * that <tt>char</tt> is a 16-bit type; to read the next 16 bits as a char,
   * use <tt>readChar(16)</tt>
   * 
   * @return the next 8 bits of data from the stream as a {@code char}
   * @throws IOException if an error occurs during I/O
   */
  public char readChar() throws IOException {
    if ( isEmpty() ) { throw new IllegalStateException("Nothing left to read"); }

    // if the buffer is full we can just use it completely
    if ( b == 8 ) {
      int buf = buffer;
      bufferNextInt();
      return (char)( buf & 0xff ); // bitwise and with 0xff to truncate
    }

    int buf = buffer;
    buf <<= ( 8 - b );            // shift by ( 8 - b ) bits and later append the next b bits
    final int offset = b;
    bufferNextInt();
    if ( isEmpty() ) { throw new IllegalStateException("nothing left to read"); }
    b = offset;
    buf |= ( buffer >>> b );      // shift the new buffer by the remaining b bits 
    return (char)( buf & 0xff );  // bitwise and with 0xff to truncate
    // TODO now: the above code doesn't quite work for the last character if N = 8
    // because buffer will be -1
  }

  /**
   * @param n the number of bits that shall be read
   * @return the next n bits of data 
   * @throws IOException if an error occurs during I/O
   * @throws IllegalArgumentException if not {@literal 0 < r <= 16}
   */
  public char readChar(int n) throws IOException {
    if ( n < 1 || n > 16 ) { throw new IllegalArgumentException("Illegal value of r = " + n); }
    // if r is 8 we can just read a character
    if ( n == 8 ) { return readChar(); }
    char c = 0;
    for ( int i = 0; i < n; i++ ) {
      c <<= 1;
      boolean bit = readBit();
      if ( bit ) { c |= 1; }
    }
    return c;
  }

  /**
   * @deprecated remove
   * @return the remaining bytes of data from standard input as a
   *         <tt>String</tt>
   * @throws IOException
   * @throws RuntimeException
   *           if standard input is empty or if the number of bits available on
   *           standard input is not a multiple of 8 (byte-aligned)
   */
  @Deprecated
  public String readString() throws IOException {
    if ( isEmpty() ) {
      throw new IllegalStateException("Cannot read from empty stream.");
    }

    StringBuilder sb = new StringBuilder();
    while ( !isEmpty() ) {
      char c = readChar();
      sb.append(c);
    }
    return sb.toString();
  }

  /**
   * @deprecated remove
   * Read the next 16 bits from standard input and return as a 16-bit short.
   * 
   * @return the next 16 bits of data from standard input as a <tt>short</tt>
   * @throws IOException
   * @throws RuntimeException
   *           if there are fewer than 16 bits available on standard input
   */
  @Deprecated
  public short readShort() throws IOException {
    short x = 0;
    for ( int i = 0; i < 2; i++ ) {
      char c = readChar();
      x <<= 8;
      x |= c;
    }
    return x;
  }

  /**
   * @return the next 32 bits from the stream interpreted as an {@code int}
   * @throws IOException if an error occurs during I/O
   */
  public int readInt() throws IOException {
    int r = 0;
    for ( int i = 0; i < 4; i++ ) {
      char c = readChar();
      r <<= 8;
      r |= c;
    }
    return r;
  }

  /**
   * @deprecated remove
   * Read the next r bits from standard input and return as an r-bit int.
   * 
   * @param r
   *          number of bits to read.
   * @return the next r bits of data from standard input as a <tt>int</tt>
   * @throws IOException
   * @throws RuntimeException
   *           if there are fewer than r bits available on standard input
   * @throws RuntimeException
   *           unless 1 &le; r &le; 32
   */
  @Deprecated
  public int readInt(int r) throws IOException {
    if ( r < 1 || r > 32 )
      throw new RuntimeException("Illegal value of r = " + r);

    // optimize r = 32 case
    if ( r == 32 )
      return readInt();

    int x = 0;
    for ( int i = 0; i < r; i++ ) {
      x <<= 1;
      boolean bit = readBit();
      if ( bit )
        x |= 1;
    }
    return x;
  }

  /**
   * @deprecated remove
   * @return the next 64 bits of data from the stream interpreted as a {@code long}
   * @throws IOException if an error occurs during I/O
   */
  @Deprecated
  public long readLong() throws IOException {
    long r = 0;
    for ( int i = 0; i < 8; i++ ) {
      char c = readChar();
      r <<= 8;
      r |= c;
    }
    return r;
  }

  /**
   * @deprecated remove
   * Read the next 64 bits from standard input and return as a 64-bit double.
   * 
   * @return the next 64 bits of data from standard input as a <tt>double</tt>
   * @throws IOException
   * @throws RuntimeException
   *           if there are fewer than 64 bits available on standard input
   */
  @Deprecated
  public double readDouble() throws IOException {
    return Double.longBitsToDouble(readLong());
  }

  /**
   * @deprecated remove
   * Read the next 32 bits from standard input and return as a 32-bit float.
   * 
   * @return the next 32 bits of data from standard input as a <tt>float</tt>
   * @throws IOException
   * @throws RuntimeException
   *           if there are fewer than 32 bits available on standard input
   */
  @Deprecated
  public float readFloat() throws IOException {
    return Float.intBitsToFloat(readInt());
  }

  /**
   * @deprecated remove
   * Read the next 8 bits from standard input and return as an 8-bit byte.
   * 
   * @return the next 8 bits of data from standard input as a <tt>byte</tt>
   * @throws IOException
   * @throws RuntimeException
   *           if there are fewer than 8 bits available on standard input
   */
  @Deprecated
  public byte readByte() throws IOException {
    char c = readChar();
    byte x = (byte) ( c & 0xff );
    return x;
  }
}