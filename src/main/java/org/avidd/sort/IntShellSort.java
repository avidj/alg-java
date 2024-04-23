package org.avidd.sort;

// improved Insertion/Bubble Sort
class IntShellSort implements IntSortStrategy {

  @Override
  public void sort(int[] a) {
    int incr = a.length / 2; 
    int j;
    while ( incr > 0 ) {
      for ( int i = incr; i < a.length; i++ ) {
        j = i - incr;
        while ( j >= 0 ) {
          if ( a[j] > a[j + incr] ) {
            SortUtil.swap(a, j, j + incr);
            j = j - incr;
          } else
            j = -1;
        } 
      } 
      incr = incr / 2;
    } 
  } 

}
