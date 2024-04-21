package org.avidd.sort;

public class ParallelMergeSortTest extends ParallelSortTest {

  @Override
  public SortStrategy getSort(int concurrencyThreshold) {
    return new ParallelMergeSort(concurrencyThreshold);
  }
}
