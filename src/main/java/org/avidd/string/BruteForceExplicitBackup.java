package org.avidd.string;

class BruteForceExplicitBackup implements StringSearch {

  @Override
  public int search(String pattern, String text) {
    final int n = text.length();
    final int m = pattern.length();
    int i;
    int j;
    for ( i = 0, j = 0; i < n && j < m; i++ ) {
      if ( text.charAt(i) == pattern.charAt(j) ) {
        j++;
      } else {
        i -= j;
        j = 0;
      }
    }
    if ( j == m ) {
      return i - m;
    }
    return -1;
  }
}
