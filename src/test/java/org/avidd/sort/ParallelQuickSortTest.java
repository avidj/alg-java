package org.avidd.sort;

public class ParallelQuickSortTest extends ParallelSortTest {

  @Override
  public SortStrategy getSort(int concurrencyThreshold) {
    return new ParallelQuickSort(concurrencyThreshold);
  }
}
