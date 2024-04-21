package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;
import static org.avidd.sort.SortUtil.leq;

import java.util.Comparator;
import java.util.concurrent.RecursiveTask;

/**
 * Merge sort applying various opimizations to improve performance over the classic merge sort.
 * Furthermore, this merge sort uses parallelization to benefit from multiple hardware threads.
 * 
 * @author David Kensche
 */
class ParallelOptimalMergeSort implements SortStrategy {
  private final int threshold;
  
  public ParallelOptimalMergeSort(int concurrencyThreshold) {
    threshold = concurrencyThreshold;
  }

  @Override
  public <V> void sort(V[] a, Comparator<? super V> comp) {
    @SuppressWarnings("unchecked")
    V[] aux = (V[])new Object[a.length];
    System.arraycopy(a, 0, aux, 0, a.length);
    MergeSort<V> sort = new MergeSort<V>(a, aux, comp, threshold);
    sort.sort();
  }

  private static class MergeSort<T> {
    private final Comparator<? super T> comparator;
    private final int threshold;
    private final T[] a;
    private final T[] aux;

    MergeSort(T[] aA, T[] aAux, Comparator<? super T> aComparator, int aThreshold) {
      comparator = aComparator;
      threshold = aThreshold;
      a = aA;
      aux = aAux;
    }

    public void sort() {
      MergeSortTask root = new MergeSortTask(0, a.length - 1);
      Sorts.POOL.invoke(root);
    }

    private void mergeSort(T[] a, T[] aux, int l, int r) {
      if ( r - l < 7 ) {
        PartialSort.insertionSort(a, l, r, comparator);
      } else if ( l < r ) {
        int m = ( l + r ) / 2; // divide: determine the middle
        mergeSort(aux, a, l, m); // conquer: merge left and right halves
        mergeSort(aux, a, m + 1, r);
        merge(aux, a, l, m, r); // merge solutions
      }
    }

    private void merge(T[] a, T[] aux, int l, int m, int r) {
      int i = l; // position in result array
      int j = l; // position in left subarray
      int k = m + 1; // position in right subarray
      // if the last element in the first half is <= the first in the right then we need not sort
      if ( less(a[m + 1], a[m], comparator) ) { //.compare(a[m], a[m + 1]) > 0 ) {
        while ( ( j <= m ) && ( k <= r ) ) { // until any half is exhausted
          if ( leq(a[j], a[k], comparator) ) { aux[i++] = a[j++]; }  
          else                               { aux[i++] = a[k++]; } 
        }
      }
      while ( j <= m )           { aux[i++] = a[j++]; } // copy remainder from the left half, or
      while ( k <= r )           { aux[i++] = a[k++]; } // copy remainder from the right half
    }

    private class MergeSortTask extends RecursiveTask<T[]> {
      private static final long serialVersionUID = 1L;
      private final int l;
      private final int r;
      
      private MergeSortTask(int aL, int aR) {
        l = aL;
        r = aR;
      }
      
      @Override
      protected T[] compute() {
        if ( r - l < 7 ) { 
          // cut off divide and conquer
          PartialSort.insertionSort(a, l, r, comparator);
        } else if ( r - l < threshold ) { 
          // cut off parallelization
          mergeSort(a, aux, l, r); 
        } else {
          int m = ( r + l ) / 2;
          MergeSortTask taskL = new MergeSortAuxTask(l, m);
          MergeSortTask taskR = new MergeSortAuxTask(m + 1, r);
          invokeAll(taskL, taskR);
          merge(aux, a, l, m, r);
        }
        return null;
      }
    }

    private class MergeSortAuxTask extends MergeSortTask {
      private static final long serialVersionUID = 1L;
      private final int l;
      private final int r;
      
      private MergeSortAuxTask(int aL, int aR) {
        super(aL, aR);
        l = aL;
        r = aR;
      }
      
      @Override
      protected T[] compute() {
        if ( r - l < 7 ) { 
          // cut off divide and conquer
          PartialSort.insertionSort(a, l, r, comparator);
        } else if ( r - l < threshold ) { 
          // cut off parallelization
          mergeSort(aux, a, l, r); 
        } else {
          int m = ( r + l ) / 2;
          MergeSortTask taskL = new MergeSortTask(l, m);
          MergeSortTask taskR = new MergeSortTask(m + 1, r);
          invokeAll(taskL, taskR);
          merge(a, aux, l, m, r);
        }
        return null;
      }
    }
  }
}
