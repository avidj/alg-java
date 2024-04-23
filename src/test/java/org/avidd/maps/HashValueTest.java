package org.avidd.maps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class HashValueTest {

  @Test
  public void testOverflow() {
    // There is one more value before 0 than after.
    Assert.assertThat(Math.abs(Integer.MIN_VALUE), is(equalTo(Integer.MIN_VALUE)));
    // This is the correct way to compute the hash value.
    Assert.assertThat(Math.abs(Integer.MIN_VALUE & Integer.MAX_VALUE), is(equalTo(0)));
  }

  @Test
  public void testAlgorithmComplexityAttackComponents() {
    int aaHash = "Aa".hashCode();
    int bbHash = "BB".hashCode();
    Assert.assertThat(aaHash, is(equalTo(bbHash)));
  }

  @Test
  public void testAlgorithmicComplexityAttack10() {
    algorithmicComplexityAttack(10);
  }

  @Test
  public void testAlgorithmicComplexityAttack100() {
    algorithmicComplexityAttack(100);
  }

  @Test
  public void testAlgorithmicComplexityAttack1000() {
    algorithmicComplexityAttack(1000);
  }

  @Test
  public void testAlgorithmicComplexityAttack10000() {
    algorithmicComplexityAttack(10000);
  }

  @Ignore
  @Test
  public void testAlgorithmicComplexityAttack100000() {
    algorithmicComplexityAttack(100000);
  }

  private void algorithmicComplexityAttack(int severity) {
    String[] r = new String[] { "Aa", "BB" };
    Map<String, String> map = new HashMap<>();

    for ( int i = 0; i < severity; i++ ) {
      String s = toString(r, i);
      map.put(s, s);
    }
  }

  private String toString(String[] r, int n) {
    StringBuilder string = new StringBuilder();
    appendByte(string, r, ( ( n >>> 0 ) & 0xff ));
    appendByte(string, r, ( ( n >>> 8 ) & 0xff ));
    appendByte(string, r, ( ( n >>> 16 ) & 0xff ));
    appendByte(string, r, ( ( n >>> 24 ) & 0xff ));
    return string.toString();
  }

  private void appendByte(StringBuilder string, String[] r, int b) {
    for ( int i = 0; i < 8; i++ ) {
      int bit = ( ( b >>> ( 8 - i - 1 ) ) & 1 );
      string.append(r[bit]);
    }
  }
}
