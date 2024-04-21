package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.swap;

import java.util.Comparator;

class SelectionSort implements SortStrategy {

  public <T> void sort(T[] a, Comparator<? super T> comp) {
    int min;
    for ( int i = 0; i < a.length - 1; i++ ) {
      min = i;
      for ( int j = i + 1; j < a.length; j++ ) {
        if ( less(a[j], a[min], comp) ) {
          min = j;
        }
      }
      swap(a, min, i);
    }
  }
}
