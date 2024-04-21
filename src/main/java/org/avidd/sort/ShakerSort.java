package org.avidd.sort;

import java.util.Comparator;

import static org.avidd.sort.SortUtil.geq;

class ShakerSort implements SortStrategy {

  @Override
  public <T> void sort(T[] a, Comparator<? super T> aComparator) {
    int l = 0; 
    int r = a.length - 1; 
    int k = 0; 
    while ( l < r ) { 
      for ( int j = l; j <= r - 1; j++ ) { 
        if ( geq(a[j], a[j + 1], aComparator) ) { 
          SortUtil.swap(a, j, j + 1); 
          k = j; 
        }
      }
      r = k; 
      for ( int j = r - 1; j >= l; j-- ) { 
        if ( geq(a[j], a[j + 1], aComparator) ) {
          SortUtil.swap(a, j, j + 1);
          k = j; 
        }
      }
      l = k + 1; 
    }
  }
}
