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
class LsdRadixSequenceSort<T> {
  private final int radix;
  private final SequenceHandler<T> seq;

  LsdRadixSequenceSort(int aRadix, SequenceHandler<T> aSeq) {
    radix = aRadix;
    seq = aSeq;
  }

  public void sort(T[] a) {
    // key indexed counting per character
    @SuppressWarnings("unchecked")
    T[] aux = (T[])new Object[a.length];
    int max = maxWordLength(a);

    // Key indexed counting for each element character position, starting at the last position.
    // As counting is stable, in the end the strings are sorted.
    // pos are bucket sizes of -1, 0, 1, .., R-1
    int[] pos = new int[radix + 2]; // +1 for the -1, +1 because pos[c] contains ...?
    for ( int d = max - 1; d >= 0; d-- ) {
      Arrays.fill(pos, 0);
      for ( int i = 0; i < a.length; i++ )  { pos[charAt(a[i], d) + 2]++; } // compute bucket sizes
      for ( int i = 0; i < radix + 1; i++ ) { pos[i + 1] += pos[i]; }       // cumulate sizes (pos)
      for ( int i = 0; i < a.length; i++ )  { aux[pos[charAt(a[i], d) + 1]++] = a[i]; }
      for ( int i = 0; i < a.length; i++ )  { a[i] = aux[i]; }
    }
  }

  private int charAt(T t, int d) {
    if ( d < seq.length(t) ) {
      return seq.digitAt(d);
    }
    return -1; // non-existent character is smaller than all others
  }

  private int maxWordLength(T[] a) {
    int max = 0;
    for ( T s : a ) {
      max = Math.max(max, seq.length(s));
    }
    return max;
  }
  
  interface SequenceHandler<T> {

    int length(T t);

    int digitAt(int d);
    
  }
}
