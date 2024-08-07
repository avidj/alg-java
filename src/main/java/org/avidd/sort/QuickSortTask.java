package org.avidd.sort;

import static org.avidd.sort.SortUtil.sortMedianOf3;
import static org.avidd.sort.SortUtil.swap;
import static org.avidd.sort.SortUtil.less;

import java.util.Comparator;
import java.util.concurrent.RecursiveTask;

class QuickSortTask extends RecursiveTask<Object[]> {
  private static final long serialVersionUID = 1L;
  private final int threshold;
  private final int l;
  private final int r;
  private transient final Object[] a;
  private transient final Comparator<Object> comparator;

  <T> QuickSortTask(T[] aArray, int aL, int aR, Comparator<T> aComparator, int aThreshold) {
    a = aArray;
    l = aL;
    r = aR;
    @SuppressWarnings("unchecked")
    Comparator<Object> temp = (Comparator<Object>)aComparator;
    comparator = temp;
    threshold = aThreshold;
  }

  @Override
  protected Object[] compute() {
    if ( r - l < threshold ) {
      quickSort(a, l, r);
    } else {
      int m = partition(a, l, r);
      QuickSortTask taskL = new QuickSortTask(a, l, m, comparator, threshold);
      QuickSortTask taskR = new QuickSortTask(a, m + 1, r, comparator, threshold);
      invokeAll(taskL, taskR);
    }
    return a;
  }
  
  private void quickSort(Object[] a, int l, int r) {
    if ( l < r ) {
      int m = partition(a, l, r);
      quickSort(a, l, m);
      quickSort(a, m + 1, r);
    }
  }

  private int partition(Object[] a, int l, int r) {
    int m = sortMedianOf3(a, l, ( r + l ) / 2, r, comparator);
    Object p = a[m];
    l--;
    r++;
    while ( true ) {
      do l++; while ( less(a[l], p, comparator) );
      do r--; while ( less(p, a[r], comparator) );
      if ( l < r )  { swap(a, l, r); } 
      else          { return r; }
    }
  }
}
