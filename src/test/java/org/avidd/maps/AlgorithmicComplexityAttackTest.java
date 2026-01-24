package org.avidd.maps;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.MatcherAssert;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * This test produced an algorithmic complexity attack that was fixed in Java 8.
 * The strings Aa and BB produce the same hash code. Therefore, they can be used
 * to generate large numbers of keys for a hash map that all hash into the same bucket.
 * In java 8, the hash map has been adapted to switch to balanced trees for large buckets,
 * with many collisions. Therefore, the slowdown has become neglectable.
 * @author david
 */
public class AlgorithmicComplexityAttackTest {

  @Test
  public void testOverflow() {
    // There is one more value before 0 than after.
    MatcherAssert.assertThat(Math.abs(Integer.MIN_VALUE), is(equalTo(Integer.MIN_VALUE)));
    // This is the correct way to compute the hash value.
    MatcherAssert.assertThat(Math.abs(Integer.MIN_VALUE & Integer.MAX_VALUE), is(equalTo(0)));
  }

  @Test
  public void testAlgorithmComplexityAttackComponents() {
    int aaHash = "Aa".hashCode();
    int bbHash = "BB".hashCode();
    MatcherAssert.assertThat(aaHash, is(equalTo(bbHash)));
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
  public void testAlgorithmicComplexityAttack10_000() {
    algorithmicComplexityAttack(10_000);
  }

  @Test
  public void testAlgorithmicComplexityAttack100_000() {
    algorithmicComplexityAttack(100_000);
  }

    @Test
  public void testAlgorithmicComplexityAttack10_000_000() {
    algorithmicComplexityAttack(10_000_000);
  }

  private void algorithmicComplexityAttack(int severity) {
    String[] r = new String[] { "Aa", "BB" };
    Map<String, String> map = new HashMap<>();

    int previous = toString(r, 0).hashCode();
    for ( int i = 0; i < severity; i++ ) {
      String s = toString(r, i);
      assert(previous == s.hashCode());
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
