package org.avidd.sort;


class IntShakerSort implements IntSortStrategy {

  public void sort(int[] a) {
    int l = 0;            // left end
    int r = a.length - 1; // right end
    int k = 0; 
    // MARK
    while ( l < r ) { // until l and r meet
                      // BubbleSort in l..r 
      for ( int j = l; j <= r - 1; j++ ) { // from left to right
        if ( a[j] >= a[j + 1] ) {          // if right neighbor is greater
          SortUtil.swap(a, j, j + 1);      
          k = j;                           // memorize position of smaller one
        }
      }
      r = k; // starting at the smaller element
      // MARK
      for ( int j = r - 1; j >= l; j-- ) { // BubbleSort in r..l 
        if ( a[j] >= a[j + 1] ) {
          SortUtil.swap(a, j, j + 1);
          k = j; // memorize position of larger one
        }
      }
      l = k + 1; // Start next iteration behind the last moved one
      // MARK
    }
  }

}
