package org.avidd.sort;

import static org.avidd.sort.SortUtil.swap;

/**
 * MSD Radix Sort sorts after the <b>M</b>ost <b>S</b>ignificant <b>D</b>igit
 * first.
 * 
 * Partition the array into R pieces according to the first character
 * (key-indexed counting). Recursively sort all strings starting with each
 * character (counts delineate subarrays to sort).
 * 
 * Can be extremely slow due to a huge number of small subarrays. --> cutoff to
 * insertion sort for small subarrays Examines just enough characters to sort
 * keys Number of characters examined depends on keys (DKE: if characters differ
 * early, finished early) Can be sublinear in input size.
 * 
 * Disadvantages 
 * * extra aux array 
 * * extra count arrays 
 * * inner loop has many instructions 
 * * accesses memory randomly (cache inefficient)
 * 
 * Disadvantages of quick sort 
 * * linearithmetic number of string compares 
 * * rescans many characters in keys with long prefix matches
 * 
 * --> 3-way radix quicksort
 * 
 * @author David Kensche
 */
class MsdRadixSort implements RadixSortStrategy {
  private static final int RADIX = 256; // using unicode would create way too
                                        // many subarrays
  private static final int OFFSET = '0';

  @Override
  public void sort(String[] a) {
    // key indexed counting per character
    String[] aux = new String[a.length];
    sort(a, aux, 0, a.length - 1, 0);
  }

  private void sort(String[] a, String[] aux, int lo, int hi, int d) {
    if ( hi <= lo ) { return; } // diff to LSD
    if ( hi - lo < 100 ) {
      sort(a, lo, hi, d);
      return;
    }
    // Key indexed counting for each element character position, starting at the
    // last position.
    // As counting is stable, in the end the strings are sorted.
    int[] pos = new int[RADIX + 2];
    for ( int i = lo; i <= hi; i++ )      { pos[charAt(a[i], d) + 2]++; } // diff to LSD
    for ( int i = 0; i < RADIX + 1; i++ ) { pos[i + 1] += pos[i]; }
    for ( int i = lo; i <= hi; i++ )      { aux[pos[charAt(a[i], d) + 1]++] = a[i]; } // diff to LSD
    for ( int i = lo; i <= hi; i++ )      { a[i] = aux[i - lo]; } // diff to LSD

    // diff to LSD (sort R subarrays)
    for ( int r = 0; r < RADIX; r++ ) {
      sort(a, aux, lo + pos[r], lo + pos[r + 1] - 1, d + 1);
    }
  }

  private static int charAt(String string, int d) {
    if ( d < string.length() ) {
      return string.charAt(d) - OFFSET;
    }
    return -1; // non-existent character is smaller than all others
  }

  // insertion sort subarray for string starting at d
  private static void sort(String[] a, int lo, int hi, int d) {
    for ( int i = 0; i <= hi; i++ ) {
      for ( int j = i; j > lo && less(a[j], a[j - 1], d); j-- ) {
        swap(a, j, j - 1);
      }
    }
  }

  private static boolean less(String v, String w, int d) {
    return v.substring(d).compareTo(w.substring(d)) < 0;
  }
}
