package org.avidd.sort;

import static org.avidd.sort.SortUtil.less;

import java.util.Comparator;

public class CountSort implements SortStrategy {

  /**
   * {@inheritDoc}
   * 
   * In the i-th run a[i] is compared to all a[j] with j > i. A counter for the larger of the two
   * is incremented. After n-1 iterations every value has been compared to every other. That is, the
   * counter for every value a[i] is now the number of values smaller than a[i]. This is exactly the
   * index, that a[i] shall take in the sorted array. A sorted array is then created and with this
   * the input array is overridden.
   * 
   * @param a the array to sort
   */
  public void sort(int[] a) {
    int[] b = new int[a.length];
    // Array of counter variables, initialize to 0.
    int[] z = new int[a.length];
    for ( int i = 0; i < a.length; i++ ) {
      z[i] = 0;
    }
    for ( int i = 0; i < a.length - 1; i++ ) {
      // Pairwise compare all other positions from this position on. 
      for ( int j = i + 1; j < a.length; j++ ) {
        // Increment the counter for the index with the larger value.
        if ( a[i] < a[j] )
          z[j]++;
        else
          z[i]++;
      }
    }
    for ( int i = 0; i < a.length; i++ ) {
      b[z[i]] = a[i];
    }
    for ( int i = 0; i < a.length; i++ ) {
      a[i] = b[i];
    }
  }

  @Override
  public <T> void sort(T[] a, Comparator<? super T> comparator) {
    @SuppressWarnings("unchecked")
    T[] b = (T[])new Object[a.length];
    int[] z = new int[a.length];
    for ( int i = 0; i < a.length; i++ ) {
      z[i] = 0;
    }
    for ( int i = 0; i < a.length - 1; i++ ) {
      for ( int j = i + 1; j < a.length; j++ ) {
        if ( less(a[i], a[j], comparator) )
          z[j]++;
        else
          z[i]++;
      }
    }
    for ( int i = 0; i < a.length; i++ ) {
      b[z[i]] = a[i];
    }
    for ( int i = 0; i < a.length; i++ ) {
      a[i] = b[i];
    }
  }
}
