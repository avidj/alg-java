package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.sortMedianOf3;
import static org.avidd.sort.SortUtil.swap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Select the k-th element of an unsorted arrays.
 */
public class QuickSelect {
  public static <T> T select(T[] a, int k, Comparator<? super T> aComparator) {
    Collections.shuffle(Arrays.asList(a));
    int l = 0;
    int r = a.length - 1;
    while ( r > l ) {
      int m = partition(a, l, r, aComparator);
      if      ( less(a[m], a[k], aComparator) ) { l = m + 1; }
      else if ( less(a[k], a[m], aComparator) ) { r = m - 1; }
      else              { return a[k]; }
    }
    return a[k];
  }
  
  private static <T> int partition(T[] a, int l, int r, Comparator<? super T> aComparator) {
    int m = sortMedianOf3(a, l, ( l + r ) / 2, r, aComparator);
    T p = a[m];
    l--;
    r++;
    while ( true ) {
      do l++; while ( less(a[l], p, aComparator) );
      do r--; while ( less(p, a[r], aComparator) );
      if ( l < r )  { swap(a, l, r); } 
      else          { return r; }
    }
  }
}
