package org.avidd.sort;

import static org.avidd.sort.SortUtil.sortMedianOf3;

import java.util.Arrays;
import java.util.Collections;

class IntQuickSort implements IntSortStrategy {
  public void sort(int[] toSort) {
    Collections.shuffle(Arrays.asList(toSort)); // shuffle to assert even distribution of pivots
    quickSort(toSort, 0, toSort.length - 1); // quick sort the whole array
  }

  private void quickSort(int[] a, int l, int r) {
    if ( r - l <= 10 ) {
      PartialSort.insertionSort(a, l, r);
    } else if ( l < r ) {
      int m = partition(a, l, r); // divide and
      quickSort(a, l, m); // conquer
      quickSort(a, m + 1, r);
    }
  }

  private int partition(int[] a, int l, int r) {
    int m = sortMedianOf3(a, l, ( r + l ) / 2, r);
    int p = a[m];
    l--;
    r++;
    while ( true ) {
      do l++; while ( a[l] < p );
      do r--; while ( a[r] > p );
      if ( l < r ) { SortUtil.swap(a, l, r); } 
      else         { return r; }
    }
  }
}
