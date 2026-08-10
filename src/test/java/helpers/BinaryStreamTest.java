package helpers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.Test;

/**
 * Conformance tests for {@link BinaryInputStream} and {@link BinaryOutputStream} against the
 * specification in {@code docs/binary-streams.md}. The tests exercise only the public API and the
 * underlying byte streams; they make no assumptions about implementation internals.
 */
public class BinaryStreamTest {

  /* ------------------------------------------------------------------ */
  /* Round-trip properties                                              */
  /* ------------------------------------------------------------------ */

  @Test
  public void bitSequencesOfAnyLengthRoundTrip() throws IOException {
    Random random = new Random(42);
    for ( int length = 1; length <= 100; length++ ) {
      boolean[] bits = new boolean[length];
      for ( int i = 0; i < length; i++ ) {
        bits[i] = random.nextBoolean();
      }

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      BinaryOutputStream out = new BinaryOutputStream(bytes);
      for ( boolean bit : bits ) {
        out.write(bit);
      }
      out.flush();

      BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(bytes.toByteArray()));
      for ( int i = 0; i < length; i++ ) {
        assertThat("bit " + i + " of " + length, in.readBit(), is(bits[i]));
      }
      // the final partial byte is padded with 0 bits up to the byte boundary
      int padding = ( 8 - length % 8 ) % 8;
      for ( int i = 0; i < padding; i++ ) {
        assertFalse(in.readBit(), "padding bit " + i + " after " + length + " data bits");
      }
      assertTrue(in.isEmpty());
    }
  }

  @Test
  public void intsRoundTrip() throws IOException {
    int[] values = { 0, 1, -1, 12, 255, 256, Integer.MAX_VALUE, Integer.MIN_VALUE, 0xCAFEBABE };

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    for ( int value : values ) {
      out.write(value);
    }
    out.flush();

    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(bytes.toByteArray()));
    for ( int value : values ) {
      assertThat(in.readInt(), is(value));
    }
    assertTrue(in.isEmpty());
  }

  @Test
  public void allEightBitCharsRoundTrip() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    for ( char c = 0; c < 256; c++ ) {
      out.write(c);
    }
    out.flush();

    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(bytes.toByteArray()));
    for ( char c = 0; c < 256; c++ ) {
      assertThat(in.readChar(), is(c));
    }
    assertTrue(in.isEmpty());
  }

  @Test
  public void charsOfEveryWidthRoundTrip() throws IOException {
    Random random = new Random(4711);
    for ( int r = 1; r <= 16; r++ ) {
      char max = (char)( r == 16 ? 0xFFFF : ( 1 << r ) - 1 );
      char[] values = { 0, max, (char)( random.nextInt(max + 1) ), (char)( random.nextInt(max + 1) ) };

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      BinaryOutputStream out = new BinaryOutputStream(bytes);
      for ( char value : values ) {
        out.write(value, r);
      }
      out.flush();

      BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(bytes.toByteArray()));
      for ( char value : values ) {
        assertThat("width " + r, in.readChar(r), is(value));
      }
    }
  }

  @Test
  public void mixedWritesAndReadsRoundTrip() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    out.write(true);
    out.write('z', 8);
    out.write(false);
    out.write((char)5, 3);
    out.write(-42);
    out.write(true);
    out.write((char)999, 12);
    out.flush();

    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(bytes.toByteArray()));
    assertTrue(in.readBit());
    assertThat(in.readChar(8), is('z'));
    assertFalse(in.readBit());
    assertThat(in.readChar(3), is((char)5));
    assertThat(in.readInt(), is(-42));
    assertTrue(in.readBit());
    assertThat(in.readChar(12), is((char)999));
  }

  /* ------------------------------------------------------------------ */
  /* Bit ordering and encoding                                          */
  /* ------------------------------------------------------------------ */

  @Test
  public void bitsFillBytesMostSignificantFirst() throws IOException {
    // a single 1-bit followed by flush must land in bit 7 of the only byte
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    out.write(true);
    out.flush();
    assertThat(bytes.toByteArray(), is(equalTo(new byte[] { (byte)0x80 })));
  }

  @Test
  public void byteAlignedEncodingIsIdentity() throws IOException {
    byte[] data = { 0x00, 0x01, 0x7F, (byte)0x80, (byte)0xFF, 0x42 };
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    for ( byte b : data ) {
      out.write((char)( b & 0xFF ), 8);
    }
    out.flush();
    assertThat(bytes.toByteArray(), is(equalTo(data)));
  }

  @Test
  public void intsAreWrittenBigEndian() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    out.write(0x0A0B0C0D);
    out.flush();
    assertThat(bytes.toByteArray(), is(equalTo(new byte[] { 0x0A, 0x0B, 0x0C, 0x0D })));
  }

  @Test
  public void bitwiseReadsAgreeWithByteWiseReads() throws IOException {
    byte[] data = { (byte)0xA5, 0x3C, (byte)0xF0 };

    BinaryInputStream byByte = new BinaryInputStream(new ByteArrayInputStream(data));
    BinaryInputStream byBit = new BinaryInputStream(new ByteArrayInputStream(data));
    for ( byte b : data ) {
      char fromBits = 0;
      for ( int i = 0; i < 8; i++ ) {
        fromBits = (char)( ( fromBits << 1 ) | ( byBit.readBit() ? 1 : 0 ) );
      }
      assertThat(byByte.readChar(8), is(fromBits));
      assertThat(fromBits, is((char)( b & 0xFF )));
    }
  }

  /* ------------------------------------------------------------------ */
  /* Flush and padding semantics                                        */
  /* ------------------------------------------------------------------ */

  @Test
  public void flushPadsTheFinalPartialByteWithZeros() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    out.write(true);
    out.write(true);
    out.write(true);
    out.flush();
    assertThat(bytes.toByteArray(), is(equalTo(new byte[] { (byte)0xE0 })));
  }

  @Test
  public void writesAfterFlushStartAFreshByte() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    out.write(true);
    out.flush();
    out.write(true);
    out.flush();
    assertThat(bytes.toByteArray(), is(equalTo(new byte[] { (byte)0x80, (byte)0x80 })));
  }

  @Test
  public void flushWithoutBufferedBitsWritesNothing() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    out.write('x', 8);
    out.flush();
    out.flush();
    assertThat(bytes.toByteArray(), is(equalTo(new byte[] { 'x' })));
  }

  @Test
  public void closeFlushesBufferedBits() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BinaryOutputStream out = new BinaryOutputStream(bytes);
    out.write(true);
    out.close();
    assertThat(bytes.toByteArray(), is(equalTo(new byte[] { (byte)0x80 })));
  }

  /* ------------------------------------------------------------------ */
  /* End-of-stream semantics                                            */
  /* ------------------------------------------------------------------ */

  @Test
  public void emptyStreamIsEmptyImmediately() throws IOException {
    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(new byte[0]));
    assertTrue(in.isEmpty());
    assertThrows(EOFException.class, in::readBit);
  }

  @Test
  public void isEmptyLoopReadsExactlyTheBytesOfTheStream() throws IOException {
    byte[] data = "ABRACADABRA!".getBytes();
    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(data));
    StringBuilder read = new StringBuilder();
    while ( !in.isEmpty() ) {
      read.append(in.readChar(8));
    }
    assertThat(read.toString(), is("ABRACADABRA!"));
  }

  @Test
  public void isEmptyDoesNotConsumeBits() throws IOException {
    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(new byte[] { (byte)0x80 }));
    assertFalse(in.isEmpty());
    assertFalse(in.isEmpty());
    assertTrue(in.readBit());
  }

  @Test
  public void readPastEndOfStreamThrowsEof() throws IOException {
    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(new byte[] { 0x00, 0x00 }));
    assertThrows(EOFException.class, in::readInt);
  }

  /* ------------------------------------------------------------------ */
  /* Argument validation                                                */
  /* ------------------------------------------------------------------ */

  @Test
  public void writeCharRejectsValuesWiderThanEightBits() {
    BinaryOutputStream out = new BinaryOutputStream(new ByteArrayOutputStream());
    assertThrows(IllegalArgumentException.class, () -> out.write((char)256));
  }

  @Test
  public void writeCharRejectsIllegalWidths() {
    BinaryOutputStream out = new BinaryOutputStream(new ByteArrayOutputStream());
    assertThrows(IllegalArgumentException.class, () -> out.write('a', 0));
    assertThrows(IllegalArgumentException.class, () -> out.write('a', 17));
    assertThrows(IllegalArgumentException.class, () -> out.write((char)8, 3));
  }

  @Test
  public void readCharRejectsIllegalWidths() throws IOException {
    BinaryInputStream in = new BinaryInputStream(new ByteArrayInputStream(new byte[] { 0x00 }));
    assertThrows(IllegalArgumentException.class, () -> in.readChar(0));
    assertThrows(IllegalArgumentException.class, () -> in.readChar(17));
  }
}
