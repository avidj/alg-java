package org.avidd.compression;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

public abstract class CompressionStrategyTest {

  protected abstract CompressionStrategy getStrategy();

  @Test
  public void testAbracadabra() throws IOException {
    final String string = "ABRACADABRA!";
    final CompressionStrategy compression = getStrategy();
    final ByteArrayOutputStream out = new ByteArrayOutputStream();

    /* do the compression */
    int bits = compression.compress(string.toCharArray(), out);

    /* assert result */
    System.out.println("\ncompressed to " + bits + " bits, compressed = '"
        + new String(out.toByteArray()) + "'");

    assertThat(hex(out.toByteArray()), is(equalTo("50 4a 22 43 43 54 a8 40 00 00 01 8f 96 8f 94")));
  }

  @Test
  public void testAbracadabraExtract() throws IOException {
    final String string = "ABRACADABRA!";
    final CompressionStrategy compression = getStrategy();
    final ByteArrayOutputStream compressed = new ByteArrayOutputStream();

    /* do the compression */
    compression.compress(string.toCharArray(), compressed);

    final InputStream compressedIn = new ByteArrayInputStream(compressed.toByteArray());
    final ByteArrayOutputStream extractedOut = new ByteArrayOutputStream();

    compression.extract(compressedIn, extractedOut);
    String extracted = new String(extractedOut.toByteArray());

    assertThat(extracted, is(equalTo(string)));
  }

  private static String hex(byte[] bytes) {
    StringJoiner joiner = new StringJoiner(" ");
    for ( byte b : bytes ) {
      joiner.add(String.format("%02x", b));
    }
    return joiner.toString();
  }
}
