package org.avidd.sort;

import java.util.concurrent.RecursiveTask;

class ParallelIntMergeSortTask extends RecursiveTask<int[]> {
  private static final long serialVersionUID = 1L;
  private final int threshold;
  private final int l;
  private final int r;
  private final int[] a;

  ParallelIntMergeSortTask(int[] aArray, int l, int r, int threshold) {
    a = aArray;
    this.l = l;
    this.r = r;
    this.threshold = threshold;
   }

  @Override
  protected int[] compute() {
    if ( r - l < threshold ) {
      mergeSort(a, l, r);
    } else {
      int m = ( l + r ) / 2;
      ParallelIntMergeSortTask taskL = new ParallelIntMergeSortTask(a, l, m, threshold);
      ParallelIntMergeSortTask taskR = new ParallelIntMergeSortTask(a, m + 1, r, threshold);
      invokeAll(taskL, taskR);
      merge(a, l, m, r);
    }
    return a;
  }

  private void mergeSort(int[] a, int l, int r) {
    if ( l < r ) {
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
      if ( a[j] <= a[k] ) { b[i++] = a[j++]; }
      else                { b[i++] = a[k++]; }
    }
    while ( j <= m ) { b[i++] = a[j++]; } 
    while ( k <= r ) { b[i++] = a[k++]; } 
    // copy results back from b to a
    for ( i = l; i <= r; i++ ) {
      a[i] = b[i - l];
    }
  }
}
