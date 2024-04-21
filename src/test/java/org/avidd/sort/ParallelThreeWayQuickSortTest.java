package org.avidd.sort;

public class ParallelThreeWayQuickSortTest extends ParallelSortTest {

  @Override
  public SortStrategy getSort(int concurrencyThreshold) {
    return new ParallelThreeWayQuickSort(concurrencyThreshold);
  }
}
