package org.avidd.sort;

import java.util.concurrent.RecursiveTask;

class ParallelIntQuickSortTask extends RecursiveTask<int[]> {
  private static final long serialVersionUID = 1L;
  private final int threshold;
  private final int l;
  private final int r;
  private final int[] a;

  public ParallelIntQuickSortTask(int[] aArray, int aL, int aR, int aThreshold) {
    threshold = aThreshold;
    a = aArray;
    l = aL;
    r = aR;
  }

  @Override
  protected int[] compute() {
    if ( r - l < threshold ) {
      quickSort(a, l, r);
    } else {
      int m = partition(a, l, r);
      ParallelIntQuickSortTask taskL = new ParallelIntQuickSortTask(a, l, m, threshold);
      ParallelIntQuickSortTask taskR = new ParallelIntQuickSortTask(a, m + 1, r, threshold);
      invokeAll(taskL, taskR);
    }
    return a;
  }

  private void quickSort(int[] a, int l, int r) {
    if ( l < r ) {
      int m = partition(a, l, r);
      quickSort(a, l, m);
      quickSort(a, m + 1, r);
    }
  }

  private int partition(int[] a, int l, int r) {
    int p = a[ ( l + r ) / 2];
    l--;
    r++;
    while ( true ) {
      do
        l++;
      while ( a[l] < p );
      do
        r--;
      while ( a[r] > p );
      if ( l < r ) {
        SortUtil.swap(a, l, r);
      } else
        return r;
    }
  }
}
