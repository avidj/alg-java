package org.avidd.sort;

import static org.avidd.sort.SortUtil.swap;

class IntHeapSort implements IntSortStrategy {

  public void sort(int[] a) {
    for ( int i = a.length / 2 - 1; i >= 0; i-- ) {
      sink(a, i, a.length - 1);
    }
    for ( int i = a.length - 1; i >= 1; i-- ) {
      swap(a, 0, i);
      sink(a, 0, i - 1);
    }
  }

  private void sink(int[] a, int from, int to) {
    from = from + 1;
    to = to + 1;
    int i = from;
    int m;
    while ( 2 * i <= to ) {
      if ( 2 * i == to ) {
        if ( a[i - 1] < a[2 * i - 1] ) {
          swap(a, i - 1, 2 * i - 1);
        }
        i = to + 1;
      } else {
        m = 2 * i;
        if ( a[2 * i - 1] < a[2 * i] ) {
          m = m + 1;
        }
        if ( a[i - 1] < a[m - 1] ) {
          swap(a, i - 1, m - 1);
          i = m;
        } else {
          i = to + 1;
        }
      }
    }
  }
}
