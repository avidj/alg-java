package org.avidd.sort;

import java.util.Comparator;
import java.util.Random;

public final class SortUtil {
  private static final Random RANDOM = new Random(System.currentTimeMillis());
  public static final Comparator<String> LEX_ORDER = new Comparator<String>() {
    @Override public int compare(String o1, String o2) { return o1.compareTo(o2); }
  }; 
  
  private SortUtil() { /* private utility class constructor */ }

  static void swap(int[] a, int i, int j) {
    int swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  public static <T> void swap(T[] a, int i, int j) {
    T swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  static <T extends Comparable<T>> boolean less(T a, T b) {
    return ( a.compareTo(b) < 0 );
  }

  static <T> boolean less(T a, T b, Comparator<? super T> comp) {
    return ( comp.compare(a, b) < 0 );
  }
  
  static <T> boolean leq(T a, T b, Comparator<? super T> comp) {
    return ( comp.compare(a, b) <= 0 );
  }

  static <T> boolean geq(T a, T b, Comparator<? super T> comp) {
    return ( comp.compare(a, b) >= 0 );
  }

  static <T> void shuffle(T[] a) {
    for ( int i = 0; i < a.length; i++ ) {
      int r = RANDOM.nextInt(i + 1);
      SortUtil.swap(a, i, r);
    }
  }
  
  static <T> int sortMedianOf3(T[] a, int l, int m, int r, Comparator<? super T> comparator) {
    if ( less(a[m], a[l], comparator) ) { swap(a, l, m); } // if m is less l place m first
    if ( less(a[r], a[l], comparator) ) { swap(a, l, r); } // if r is less l place r first
    if ( less(a[r], a[m], comparator) ) { swap(a, m, r); } // if r is less m place r first
    return m;                                              // m is now the median
  }
  
  static int sortMedianOf3(int[] a, int l, int m, int r) {
    if ( a[m] < a[l] ) { swap(a, l, m); }
    if ( a[r] < a[l] ) { swap(a, l, r); }
    if ( a[r] < a[m] ) { swap(a, m, r); }
    return m;
  }

  static void assertSorted(int[] a) {
    for ( int i = 0; i < a.length - 2; i++ ) {
      assert ( a[i] <= a[i + 1] );
    }
  }

  public static void assertSorted(String[] a) {
    for ( int i = 0; i < a.length - 2; i++ ) {
      assert ( a[i].compareTo(a[i + 1]) <= 0 );
    }
  }
}
