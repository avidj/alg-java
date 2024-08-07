package org.avidd.sort;

import java.util.Comparator;
import java.util.concurrent.RecursiveTask;

/**
 * A task for parallelizing merge sort.
 * 
 * @author David Kensche
 */
class MergeSortTask extends RecursiveTask<Object[]> {
  private static final long serialVersionUID = 1L;
  private final int threshold;
  private transient final Comparator<Object> comparator;
  private final Object[] a;
  private final int l;
  private final int r;

  /**
   * @param aComparator the comparator defining the order of array elements
   * @param aArray the array to sort
   * @param aL the leftmost index of the subarray to sort
   * @param aR the rightmost index of the subarray to sort
   * @param aThreshold a threshold for cutting off parallelization 
   */
  <T> MergeSortTask(Comparator<T> aComparator, T[] aArray, int aL, int aR, int aThreshold) {
    @SuppressWarnings("unchecked")
    Comparator<Object> temp = (Comparator<Object>)aComparator;
    comparator = temp;
    a = aArray;
    l = aL;
    r = aR;
    threshold = aThreshold;
  }

  @Override
  protected Object[] compute() {
    if ( r - l < threshold ) {
      // if the subarray to be sorted is too small we continue with sequential merge sort
      mergeSort(a, l, r);
    } else {      
      int m = ( l + r ) / 2;
      MergeSortTask taskL = new MergeSortTask(comparator, a, l, m, threshold);
      MergeSortTask taskR = new MergeSortTask(comparator, a, m + 1, r, threshold);
      invokeAll(taskL, taskR);
      merge(comparator, a, l, m, r);
    }
    return a;
  }

  /**
   * Sequential merge sort.
   * @param a the array to sort
   * @param l the leftmost index of the subarray to sort
   * @param r the rightmost index of the subarray to sort
   */
  private <T> void mergeSort(T[] a, int l, int r) {
    if ( l < r ) {
      int m = ( l + r ) / 2;
      mergeSort(a, l, m);
      mergeSort(a, m + 1, r);
      merge(comparator, a, l, m, r);
    }
  }

  private <T> void merge(Comparator<Object> comparator, Object[] a, int l, int m, int r) {
    Object[] b = new Object[r - l + 1];
    int i = 0;
    int j = l;
    int k = m + 1;
    while ( ( j <= m ) && ( k <= r ) ) {
      if ( comparator.compare(a[j], a[k]) <= 0 ) { b[i++] = a[j++]; }
      else                                       { b[i++] = a[k++]; }
    }
    while ( j <= m ) { b[i++] = a[j++]; }
    while ( k <= r ) { b[i++] = a[k++]; }
    for ( i = l; i <= r; i++ ) { 
      a[i] = b[i - l];
    }
  }
}
