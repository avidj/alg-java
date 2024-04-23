package org.avidd.sort;


class IntSelectionSort implements IntSortStrategy {

  @Override
  public void sort(int[] a) {
    int min;
    for ( int i = 0; i < a.length - 1; i++ ) {
      min = i;
      for ( int j = i + 1; j < a.length; j++ ) {
        if ( a[j] < a[min] ) {
          min = j;
        }
      }
      SortUtil.swap(a, min, i);
    }
  }
}
