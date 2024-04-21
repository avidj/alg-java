package org.avidd.sort;

/**
 * Merge sort parallelized with a {@link java.util.concurrent.ForkJoinPool}.
 * 
 * @author David Kensche
 */
class ParallelIntMergeSort implements IntSortStrategy {
  private final int threshold; // threshold for pruning parallelization

  /**
   * Create a parallel merge sort that prunes parallelization at problem size of 
   * {@literal aThreshold}.
   * 
   * @param aThreshold problems smaller than this threshold are not parallelized
   */
  ParallelIntMergeSort(int aThreshold) {
    threshold = aThreshold;
  }

  @Override
  public void sort(int[] a) {
    ParallelIntMergeSortTask root = new ParallelIntMergeSortTask(a, 0, a.length - 1, threshold);
    Sorts.POOL.invoke(root);
  }
}
