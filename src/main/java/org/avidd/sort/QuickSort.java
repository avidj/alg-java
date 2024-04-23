package org.avidd.sort;

import static org.avidd.sort.SortUtil.shuffle;
import static org.avidd.sort.SortUtil.sortMedianOf3;

import java.util.Comparator;

class QuickSort implements SortStrategy {
    
  @Override
  public <T> void sort(T[] a, Comparator<? super T> comparator) {
    shuffle(a);
    quickSort(comparator, a, 0, a.length - 1);
  }

  private <T> void quickSort(Comparator<? super T> comparator, T[] a, int l, int r) {
    if ( r - l <= 10 ) {
      PartialSort.insertionSort(a, l, r, comparator);
    } else if ( l < r ) {
      sortMedianOf3(a, l, ( r + l ) / 2, r, comparator);
      int m = partition(comparator, a, l, r);
      quickSort(comparator, a, l, m);
      quickSort(comparator, a, m + 1, r);
    }
  }

  private <T> int partition(Comparator<T> comparator, T[] a, int l, int r) {
    T p = a[ ( l + r ) / 2];
    l--;
    r++;
    while ( true ) {
      do l++; while ( comparator.compare(a[l], p) < 0 );
      do r--; while ( comparator.compare(a[r], p) > 0 );
      if ( l < r ) SortUtil.swap(a, l, r);
      else return r;
    }
  }
}
