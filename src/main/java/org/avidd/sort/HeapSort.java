package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.swap;

import java.util.Comparator;

/**
 * Elegant and theoretically fast sort. However, due to almost random array accesses cannot be
 * optimized by the compiler. 
 */
class HeapSort implements SortStrategy {

  @Override
  public <T> void sort(T[] a, Comparator<? super T> comparator) {    
    // create the heap
    for ( int i = a.length / 2 - 1; i >= 0; i-- ) {
      sink(a, i, a.length - 1, comparator);
    }
    // sort phase
    for ( int i = a.length - 1; i >= 1; i-- ) {
      swap(a, 0, i);
      sink(a, 0, i - 1, comparator);
    }
  }

  // sink the given element until all its children are smaller or equal
  private <T> void sink(T[] a, int from, int to, Comparator<T> comparator) {
    from = from + 1;
    to = to + 1;
    int i = from;
    int m;
    while ( 2 * i <= to ) {
      if ( 2 * i == to ) {
        if ( less(a[i - 1], a[2 * i - 1], comparator) ) {
          swap(a, i - 1, 2 * i - 1);
        }
        i = to + 1;
      } else {
        m = 2 * i;
        if ( less(a[2 * i - 1], a[2 * i], comparator) ) {
          m = m + 1;
        }
        if ( less(a[i - 1], a[m - 1], comparator) ) {
          swap(a, i - 1, m - 1);
          i = m;
        } else {
          i = to + 1;
        }
      }
    }
  }
}
