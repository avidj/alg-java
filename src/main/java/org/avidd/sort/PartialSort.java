package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.swap;

import java.util.Comparator;

final class PartialSort {
  private PartialSort() { /* hidden utility class constructor */ }
  
  static <T> void insertionSort(T[] a, int l, int r, Comparator<T> aComparator) {
    for ( int i = l + 1; i <= r; i++ ) {
      for ( int j = i; j > l; j-- ) {
        if ( less(a[j], a[j - 1], aComparator) ) {
          swap(a, j, j - 1);
        }
      }
    }
  }

  static void insertionSort(int[] a, int l, int r) {
    for ( int i = l + 1; i <= r; i++ ) {
      for ( int j = i; j > l; j-- ) {
        if ( a[j] < a[j - 1] ) {
          swap(a, j, j - 1);
        }
      }
    }
  }  
}
