package org.avidd.sort;

public class ParallelIntMergeSortTest extends ParallelIntSortTest {

  @Override
  public IntSortStrategy getSort(int concurrencyThreshold) {
    return new ParallelIntMergeSort(concurrencyThreshold);
  }
}
