package org.avidd.string;

public class Strings {

  public static String longestCommonPrefix(String s, String t) {
    int length = longestCommonPrefixLength(s, t);
    return s.substring(0, length);
  }

  public static int longestCommonPrefixLength(String s, String t) {
    int n = Math.min(s.length(), t.length());
    for ( int i = 0; i < n; i++ ) {
      if ( s.charAt(i) != t.charAt(i) ) {
        return i;
      }
    }
    return n;
  }

}
