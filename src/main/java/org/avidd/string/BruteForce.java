package org.avidd.string;

public class BruteForce implements StringSearch {

  @Override
  public int search(String pattern, String text) {
    final int n = text.length();
    final int m = pattern.length();
    for ( int i = 0, k = n - m + 1; i < k; i++ ) {
      int j;
      for ( j = 0; j < m; j++ ) {
        if ( text.charAt(i + j) != pattern.charAt(j) ) {
          break;
        }
      }
      if ( j == m ) {
        return i;
      }
    }
    return -1;
  }

}
