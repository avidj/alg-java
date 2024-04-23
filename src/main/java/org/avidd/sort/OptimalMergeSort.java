package org.avidd.sort;

import java.util.Comparator;

/**
 * Merge sort applying various opimizations to improve performance over the classic merge sort. 
 */
class OptimalMergeSort implements SortStrategy {

  @Override
  public <V> void sort(V[] a, Comparator<? super V> comp) {
    MergeSort<V> sort = new MergeSort<>(comp);
    sort.sort(a);
  }

  private static class MergeSort<T> {
    private final Comparator<? super T> comparator;

    MergeSort(Comparator<? super T> aComparator) {
      comparator = aComparator;
    }

    public void sort(T[] a) {
      @SuppressWarnings("unchecked")
      T[] aux = (T[])new Object[a.length];
      System.arraycopy(a, 0, aux, 0, a.length);
      mergeSort(a, aux, 0, a.length - 1);
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
      if ( comparator.compare(a[m], a[m + 1]) > 0 ) {
        while ( ( j <= m ) && ( k <= r ) ) { // until any half is exhausted
          if ( comparator.compare(a[j], a[k]) <= 0 ) { 
            aux[i++] = a[j++]; // copy value from left if <= right 
          } else { 
            aux[i++] = a[k++]; 
          } // copy value from right if < than left
        }
      }
      while ( j <= m )           { aux[i++] = a[j++]; } // copy remainder from the left half, or
      while ( k <= r )           { aux[i++] = a[k++]; } // copy remainder from the right half
    }
  }
}
