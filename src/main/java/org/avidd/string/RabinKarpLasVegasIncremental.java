package org.avidd.string;

/**
 * Linear time substring matching using the Rabin-Karp algorithm that uses
 * modular hashing to quickly find a position in the test that may match the
 * pattern. This is the Las-Vegas version that checks actual equality if a
 * position has the same hash as the pattern.
 */
public class RabinKarpLasVegasIncremental implements StringSearch {
  private static final long R = 31;
  private static final long Q = 997;

  @Override
  public int search(String pattern, String text) {
    final int m = pattern.length();
    final int n = text.length();
    if ( n < m ) {
      return -1;
    } // no match if pattern shorter than text
    long sm = 1; // factor for most-significant character (the one falling out on each iteration)
    for ( int i = 1; i < m; i++ )
      sm = ( sm * R ) % Q;
    long pHash = hash(pattern, 0, m); // compute the hash for the pattern
    long tHash = hash(text, 0, m); // hash of first m chars in text

    for ( int i = m; i <= n; i++ ) {
      if ( tHash == pHash && pattern.equals(text.substring(i - m, i)) )
        return i - m;
      if ( i < n ) {
        // + Q because tHash - sm * text.charAt(i - m) % Q may be < 0
        tHash = ( tHash + Q - sm * text.charAt(i - m) % Q ) % Q;
        // shift hash one position to the left and add the value for the least
        // significant character
        tHash = ( tHash * R + text.charAt(i) ) % Q;
      }
    }
    return -1;
  }

  private static long hash(String text, int i, int j) {
    long hash = 0;
    for ( ; i < j; i++ )
      hash = ( hash * R + text.charAt(i) ) % Q;
    return hash;
  }
}
