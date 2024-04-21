package org.avidd.sort;

import static org.avidd.sort.SortUtil.leq;
import java.util.Comparator;

class MergeSort implements SortStrategy {

  public <T> void sort(T[] a, Comparator<? super T> aComparator) {
    mergeSort(a, 0, a.length - 1, aComparator);
  }

  private <T> void mergeSort(T[] a, int l, int r, Comparator<? super T> aComparator) {
    if ( l < r ) {
      int m = ( l + r ) / 2; // divide: determine the middle
      mergeSort(a, l, m, aComparator); // conquer: merge left and right halves
      mergeSort(a, m + 1, r, aComparator);
      merge(a, l, m, r, aComparator); // merge solutions
    }
  }

  private <T> void merge(T[] a, int l, int m, int r, Comparator<T> aComparator) {
    @SuppressWarnings("unchecked")
    T[] b = (T[])new Object[r - l + 1];
    int i = 0; // position in result array
    int j = l; // position in left subarray
    int k = m + 1; // position in right subarray
    while ( ( j <= m ) && ( k <= r ) ) { // until any half is exhausted
      if ( leq(a[j], a[k], aComparator) ) { b[i++] = a[j++]; } 
      else                                { b[i++] = a[k++]; } 
    }
    while ( j <= m )                      { b[i++] = a[j++]; } 
    while ( k <= r )                      { b[i++] = a[k++]; } 
    for ( i = l; i <= r; i++ ) { a[i] = b[i - l]; } // copy results back from b to a
  }
}
