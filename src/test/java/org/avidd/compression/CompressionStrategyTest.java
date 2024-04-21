package org.avidd.compression;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

import coursera.programs.HexDump;

public abstract class CompressionStrategyTest {

  protected abstract CompressionStrategy getStrategy();

  @Test
  public void testAbracadabra() throws IOException {
    final String string = "ABRACADABRA!";
    final CompressionStrategy compression = getStrategy();
    final InputStream in = new ByteArrayInputStream(string.getBytes());
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    HexDump.dump(in, System.out);

    /* do the compression */
    // int bits = compression.compress(in, out);
    int bits = compression.compress(string.toCharArray(), out);

    /* assert result */
    System.out.println("compressed:");
    HexDump.dump(new ByteArrayInputStream(out.toByteArray()), System.out);
    System.out.println("\ncompressed to " + bits + " bits, compressed = '"
        + new String(out.toByteArray()) + "'");

    StringBuilder result = new StringBuilder();
    HexDump.dump(new ByteArrayInputStream(out.toByteArray()), result);
    assertThat(result.toString(), is(equalTo("50 4a 22 43 43 54 a8 40 00 00 01 8f 96 8f 94")));
  }

  @Test
  public void testAbracadabraExtract() throws IOException {
    final String string = "ABRACADABRA!";
    final CompressionStrategy compression = getStrategy();
//    final InputStream original = new ByteArrayInputStream(string.getBytes());
    final ByteArrayOutputStream compressed = new ByteArrayOutputStream();

    /* do the compression */
    compression.compress(string.toCharArray(), compressed);

    final InputStream compressedIn = new ByteArrayInputStream(compressed.toByteArray());
    final ByteArrayOutputStream extractedOut = new ByteArrayOutputStream();

    compression.extract(compressedIn, extractedOut);
    String extracted = new String(extractedOut.toByteArray());

    Assert.assertThat(extracted, is(equalTo(string)));
  }
}
