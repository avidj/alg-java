package org.avidd.sort;

/**
 * Insertion sort for integer arrays.
 */
class IntInsertionSort implements IntSortStrategy {

  @Override
  public void sort(int[] a) {
    for ( int i = 1; i < a.length; i++ ) {
      for ( int j = i; j > 0 && a[j] < a[j - 1]; j--) { 
        SortUtil.swap(a, j - 1, j);
      }
    }
  }
}
