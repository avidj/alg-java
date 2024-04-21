package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.swap;

import java.util.Comparator;

class ShellSort implements SortStrategy {

  @Override
  public <T> void sort(T[] a, Comparator<? super T> comp) {
    final int n = a.length;
    int h = 1;
    while ( h < a.length / 3 ) { h = 3 * h + 1; }
    while ( h > 0 ) {
      for ( int i = h; i < n; i += h )
        for ( int j = i; j >= h && less(a[j], a[j - h], comp); j -= h )
          swap(a, j, j - h);
      h = h / 3;
    }
  }

}
