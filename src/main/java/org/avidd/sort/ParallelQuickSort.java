package org.avidd.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

/**
 * Quick sort parallelized with a {@link java.util.concurrent.ForkJoinPool}.
 * 
 * @author David Kensche
 */
class ParallelQuickSort implements SortStrategy {
  protected int threshold; // threshold for pruning parallelization

  /**
   * Create a parallel quick sort that prunes parallelization at problem size of 
   * {@literal aThreshold}.
   * 
   * @param aThreshold problems smaller than this threshold are not parallelized
   */
  ParallelQuickSort(int aThreshold) {
    threshold = aThreshold;
  }

  @Override
  public <T> void sort(T[] a, Comparator<? super T> comp) {
    Collections.shuffle(Arrays.asList(a)); // shuffle to assert even distribution of pivots
    QuickSortTask root = new QuickSortTask(a, 0, a.length - 1, comp, threshold);
    Sorts.POOL.invoke(root);
  }
}
