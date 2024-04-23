package org.avidd.sort;

class IntMergeSort implements IntSortStrategy {

  @Override
  public void sort(int[] a) {
    mergeSort(a, 0, a.length - 1);
  }

  private void mergeSort(int[] a, int l, int r) {
    if ( r - l < 7 ) {
      PartialSort.insertionSort(a, l, r);
    } else if ( l < r ) {
      int m = ( l + r ) / 2; // divide: determine the middle
      mergeSort(a, l, m); // conquer: merge left and right halves
      mergeSort(a, m + 1, r);
      merge(a, l, m, r); // merge solutions
    }
  }

  private void merge(int[] a, int l, int m, int r) {
    int[] b = new int[r - l + 1];
    int i = 0; // position in result array
    int j = l; // position in left subarray
    int k = m + 1; // position in right subarray
    while ( ( j <= m ) && ( k <= r ) ) { // until any half is exhausted
      if ( a[j] <= a[k] ) { b[i++] = a[j++]; } // copy value from left if <= right
      else                { b[i++] = a[k++]; } // copy value from right if < than left
    }
    while ( j <= m ) { b[i++] = a[j++]; } // copy remainder from the left half, or
    while ( k <= r ) { b[i++] = a[k++]; } // copy remainder from the right half
    for ( i = l; i <= r; i++ ) { a[i] = b[i - l]; } 
  }
}
