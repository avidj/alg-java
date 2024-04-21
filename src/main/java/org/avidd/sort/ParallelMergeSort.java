package org.avidd.sort;

import java.util.Comparator;

/**
 * Parallel merge sort using a fork join pool.
 * @author David Kensche
 */
class ParallelMergeSort implements org.avidd.sort.SortStrategy {
  private final int threshold;

  ParallelMergeSort(int aThreshold) {
    threshold = aThreshold;
  }

  @Override
  public <T> void sort(T[] a, Comparator<? super T> comparator) {
    MergeSortTask root = new MergeSortTask(comparator, a, 0, a.length - 1, threshold);
    Sorts.POOL.invoke(root);
  }
}
