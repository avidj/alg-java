package org.avidd.string;

/**
 * Given a string an implementation will return the longest repeated substring.
 * Applications are in genomics.
 */
public abstract class LongestRepeatedSubstring {

  public String lrs(String s) {
    String[] suffixes = new String[s.length()];
    for ( int i = 0, n = s.length(); i < n; i++ ) {
      suffixes[i] = s.substring(i);
    }
    sort(suffixes);
    String lrs = "";
    for ( int i = 0, n = s.length(); i < n - 1; i++ ) {
      int len = lcp(suffixes[i], suffixes[i + 1]);
      if ( len > lrs.length() ) {
        lrs = suffixes[i].substring(0, len);
      }
    }
    return lrs;
  }

  protected abstract void sort(String[] suffixes);

  private static int lcp(String s, String t) {
    int N = Math.min(s.length(), t.length());
    for ( int i = 0; i < N; i++ ) {
      if ( s.charAt(i) != t.charAt(i) ) {
        return i;
      }
    }
    return N;
  }
}
