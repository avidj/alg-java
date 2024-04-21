package org.avidd.sort;

import static org.avidd.sort.SortUtil.swap;

import java.util.Arrays;
import java.util.Collections;

class IntThreeWayQuickSort implements IntSortStrategy {
  
  @Override
  public void sort(int[] toSort) {
    Collections.shuffle(Arrays.asList(toSort)); // shuffle to assert even distribution of pivots
    quickSort(toSort, 0, toSort.length - 1);    // quick sort the whole array
  }

  private void quickSort(int[] a, int l, int r) {
    if ( r - l <= 10 ) {
      PartialSort.insertionSort(a, l, r);
    } else if ( l < r ) {
      int lt = l;
      int gt = r;
      int i = l;
      int val = a[l];
      while ( i <= gt ) {
        if      ( a[i] < val ) { swap(a, lt++, i++); }
        else if ( val < a[i] ) { swap(a, i, gt--); }
        else                   { i++; }
      }
      quickSort(a, l, lt - 1);
      quickSort(a, gt + 1, r);
    }
  }
}
