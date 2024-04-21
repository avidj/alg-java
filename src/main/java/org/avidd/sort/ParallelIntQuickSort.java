package org.avidd.sort;

import java.util.Arrays;
import java.util.Collections;

/**
 * Quick sort parallelized with a {@link java.util.concurrent.ForkJoinPool}.
 * 
 * @author David Kensche
 */
class ParallelIntQuickSort implements IntSortStrategy {
  protected int threshold; // threshold for pruning parallelization

  /**
   * Create a parallel quick sort that prunes parallelization at problem size of 
   * {@literal aThreshold}.
   * 
   * @param aThreshold problems smaller than this threshold are not parallelized
   */
  ParallelIntQuickSort(int aThreshold) {
    threshold = aThreshold;
  }

  public void sort(int[] a) {
    Collections.shuffle(Arrays.asList(a)); // shuffle to assert even
                                           // distribution of pivots
    ParallelIntQuickSortTask root = new ParallelIntQuickSortTask(a, 0, a.length - 1, threshold);
    Sorts.POOL.invoke(root);
  }
}
