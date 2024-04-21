package org.avidd.sort;

import java.util.Arrays;

/**
 * LSD Radix Sort sorts after the <b>L</b>east <b>S</b>ignificant <b>D</b>igit
 * first.
 * 
 * LSD Radix Sort is a string based sort strategy. This allows it to run faster
 * than the compare-based algorithms like quick sort or merge sort, namely in
 * O(n * m) where m is the maximal input string length. Moreover, this strategy
 * is stable. However, Radix Sort requires a (potentially) large amount of extra
 * memory, namely an array of the size of the radix. So the benefit exists only
 * for large amounts of strings to be sorted.
 * 
 * @author David Kensche
 */
class LsdRadixSort implements RadixSortStrategy {
  private static final int RADIX = 256;

  @Override
  public void sort(String[] a) {
    // key indexed counting per character
    String[] aux = new String[a.length];
    int max = maxWordLength(a);

    // Key indexed counting for each element character position, starting at the last position.
    // As counting is stable, in the end the strings are sorted.
    // pos are bucket sizes of -1, 0, 1, .., R-1
    int[] pos = new int[RADIX + 2]; // +1 for the -1 (to hit 0), +1 because pos[c] contains cum pos?
    for ( int d = max - 1; d >= 0; d-- ) {
      Arrays.fill(pos, 0);
      for ( int i = 0; i < a.length; i++ )  { pos[charAt(a[i], d) + 2]++; } // compute bucket sizes
      for ( int i = 0; i < RADIX + 1; i++ ) { pos[i + 1] += pos[i]; }       // cumulate sizes (pos)
      for ( int i = 0; i < a.length; i++ )  { aux[pos[charAt(a[i], d) + 1]++] = a[i]; }
      for ( int i = 0; i < a.length; i++ )  { a[i] = aux[i]; }
    }
  }

  private static int charAt(String string, int d) {
    if ( d < string.length() ) {
      return string.charAt(d);
    }
    return -1; // non-existent character is smaller than all others
  }

  private static int maxWordLength(String[] a) {
    int max = 0;
    for ( String s : a ) {
      max = Math.max(max, s.length());
    }
    return max;
  }
}
