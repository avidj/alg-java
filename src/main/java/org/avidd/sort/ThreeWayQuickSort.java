package org.avidd.sort;

import static org.avidd.sort.SortUtil.swap;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

class ThreeWayQuickSort implements SortStrategy {
  
  @Override
  public <T> void sort(T[] toSort, Comparator<? super T> aComparator) {
    Collections.shuffle(Arrays.asList(toSort));           // assert even distribution of pivots
    quickSort(toSort, 0, toSort.length - 1, aComparator); // quick sort the whole array
  }

  private <T> void quickSort(T[] a, int l, int r, Comparator<? super T> aComparator) {
    if ( r - l <= 10 ) {
      PartialSort.insertionSort(a, l, r, aComparator);
    } else if ( l < r ) {
      int lt = l;
      int gt = r;
      int i = l;
      T val = a[l];
      while ( i <= gt ) {
        int compare = aComparator.compare(a[i], val);
        if      ( compare < 0 ) { swap(a, lt++, i++); }
        else if ( compare > 0 ) { swap(a, i, gt--); }
        else                    { i++; }
      }
      quickSort(a, l, lt - 1, aComparator);
      quickSort(a, gt + 1, r, aComparator);
    }
  }
}
