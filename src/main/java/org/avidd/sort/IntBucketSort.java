package org.avidd.sort;

/**
 * To enable a list to be sorted with bucket sort, the number of possible
 * elements in the list must be finite. Furthermore, for the algorithm to be
 * SPACE-efficient the number of elements in the list should be considerably
 * higher than the number of possible elements that are used for sorting.
 * Therefore, bucket sort is very appropriate to sort a long list of zips of
 * cities but is not a good choice to sort persons according to their names.
 * 
 * The implementation is eased if the sorting attribute is the set of integers.
 * In this case they can be used as indices of an array. Otherwise, an
 * additional bijection function is necessary that assigns a unique index to
 * each possible key value, as well as its inverse function.
 * 
 * Complexity: To compute the histogram, the input array is passed once. The
 * complexity thus increases linearly in the size of the input array. Thus, it
 * is O(n).
 * 
 * To compute the input positions the histogram is passed once. For each entry
 * the cumulation of the preceding entries is added. The histogram is as long as
 * the number of possible key values. Consequently this step is O(k).
 * 
 * Then the array to be sorted is passed once more to copy values into the
 * result array into the computed positions. The complexity is once more O(n).
 * 
 * In the end the complexity is O(n+k).
 * 
 * @author David Kensche
 */
public class IntBucketSort implements IntSortStrategy {

  private static void algorithm(int[] a, int m) {
    // create result array
    int[] result = new int[a.length];
    // create histogram array
    int[] count = new int[m];
    // initialize histogram
    for ( int i = 0; i < m; i++ ) {
      count[i] = 0;
    }
    // create histogram
    for ( int i = 0; i < a.length; i++ ) {
      count[a[i]]++;
    }
    // compute result positions (sums of preceding values.
    for ( int i = 1; i < m; i++ ) {
      count[i] = count[i] + count[i - 1];
    }
    // copy values to computed positions and decrease counters
    for ( int i = a.length - 1; i >= 0; i-- ) {
      result[count[a[i]] - 1] = a[i];
      count[a[i]]--;
    }
    // copy result into input array
    for ( int i = 0; i < a.length; i++ ) {
      a[i] = result[i];
    }
  }

  public void sort(int[] a) {
    int max = 0;
    for ( int i = 0; i < a.length; i++ ) {
      if ( max < a[i] ) {
        max = a[i];
      }
    }
    algorithm(a, max + 1);
  }
}