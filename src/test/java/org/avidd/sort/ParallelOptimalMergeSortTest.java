package org.avidd.sort;

public class ParallelOptimalMergeSortTest extends ParallelSortTest {

  @Override
  public SortStrategy getSort(int concurrencyThreshold) {
    return new ParallelOptimalMergeSort(concurrencyThreshold);
  }
}
