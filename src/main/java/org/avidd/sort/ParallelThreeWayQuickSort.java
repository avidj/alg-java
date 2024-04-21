package org.avidd.sort;

import static org.avidd.sort.SortUtil.swap;

import java.util.Comparator;
import java.util.concurrent.RecursiveTask;

class ParallelThreeWayQuickSort implements SortStrategy {
  private final int threshold;
  
  public ParallelThreeWayQuickSort(int concurrencyThreshold) {
    threshold = concurrencyThreshold;
  }

  @Override
  public <T> void sort(T[] toSort, Comparator<? super T> aComparator) {
    ThreeWayQuickSort<T> twq = new ThreeWayQuickSort<T>(toSort, aComparator, threshold);
    twq.sort();
  }

  private static class ThreeWayQuickSort<T> {
    private final T[] a;
    private final Comparator<? super T> comparator;
    private final int threshold;
    
    public ThreeWayQuickSort(T[] aA, Comparator<? super T> aComparator, int aThreshold) {
      a = aA;
      comparator = aComparator;
      threshold = aThreshold;
    }

    public void sort() {
      TWQTask root = new TWQTask(0, a.length - 1);
      @SuppressWarnings("unused")
      T[] result = Sorts.POOL.invoke(root);
    }

    private void quickSort(T[] a, int l, int r, Comparator<? super T> aComparator) {
      if ( r - l <= 10 ) {
        PartialSort.insertionSort(a, l, r, aComparator);
      } else if ( l < r ) {
        int lt = l;
        int gt = r;
        int i = l;
        T val = a[l];
        while ( i <= gt ) {
          int compare = aComparator.compare(a[i], val);
          if      ( compare < 0 ) { swap(a, lt++, i++); }
          else if ( compare > 0 ) { swap(a, i, gt--); }
          else                    { i++; }
        }
        quickSort(a, l, lt - 1, aComparator);
        quickSort(a, gt + 1, r, aComparator);
      }
    }

    private class TWQTask extends RecursiveTask<T[]> {
      private static final long serialVersionUID = 1L;
      private final int l;
      private final int r;
      
      public TWQTask(int aL, int aR) {
        l = aL;
        r = aR;
      }

      @Override
      protected T[] compute() {
        if ( r - l <= 10 ) {
          PartialSort.insertionSort(a, l, r, comparator); // cutoff quicksort
        } else if ( r - l < threshold ) {
          quickSort(a, l, r, comparator);                 // cutoff parallelization
        } else if ( l < r ) {
          int lt = l;
          int gt = r;
          int i = l;
          T val = a[l];
          while ( i <= gt ) {
            int compare = comparator.compare(a[i], val);
            if      ( compare < 0 ) { swap(a, lt++, i++); }
            else if ( compare > 0 ) { swap(a, i, gt--); }
            else                    { i++; }
          }
          TWQTask lTask = new TWQTask(l, lt - 1);
          TWQTask rTask = new TWQTask(gt + 1, r);
          invokeAll(lTask, rTask);
        }
        return a;
      }
    }
  }  
}
