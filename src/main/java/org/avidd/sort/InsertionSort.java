package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.swap;

import java.util.Comparator;

class InsertionSort implements SortStrategy {

  @Override
  public <T> void sort(T[] a, Comparator<? super T> comparator) {
    for ( int i = 1; i <= a.length - 1; i++ )
      for ( int j = i; j > 0; j-- )
        if ( less(a[j], a[j - 1], comparator) )
          swap(a, j, j - 1);
  }
}
