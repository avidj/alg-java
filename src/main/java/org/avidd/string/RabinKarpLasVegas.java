package org.avidd.string;

public class RabinKarpLasVegas implements StringSearch {
  private static final int HASH_SHIFT = 31;
  private static final int MOD = 997;

  @Override
  public int search(String pattern, String text) {
    if ( text.length() < pattern.length() ) {
      return -1;
    }
    final int patternHash = hash(pattern, 0, pattern.length());
    for ( int i = 0, n = text.length() - pattern.length() + 1; i < n; i++ ) {
      if ( hash(text, i, i + pattern.length()) == patternHash
          && pattern.equals(text.substring(i, i + pattern.length())) ) {
        return i;
      }
    }
    return -1;
  }

  private int hash(String pattern, int i, int j) {
    int hash = 0;
    for ( ; i < j; i++ ) {
      hash = ( hash * HASH_SHIFT + (int)pattern.charAt(i) ) % MOD;
    }
    return hash;
  }

}
