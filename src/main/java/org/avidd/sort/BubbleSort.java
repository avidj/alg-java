package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.swap;

import java.util.Comparator;

/**
 * The pretty dumb bubble sort that bubbles up elements to the end of the array. 
 */
class BubbleSort implements SortStrategy {
  @Override
  public <T> void sort(T[] a, Comparator<? super T> comparator) {
    // the largest element bubbles up to the end a.length -1
    for ( int last = a.length - 1; last >= 2; last-- ) {
      // swapping starts with the first two elements
      for ( int j = 1; j <= last; j++ ) {
        if ( less(a[j], a[j - 1], comparator) ) {
          // swap smaller with larger elements 
          swap(a, j - 1, j);
        }
      }
    }	
  }
}
