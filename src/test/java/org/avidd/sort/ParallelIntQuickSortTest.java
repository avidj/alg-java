package org.avidd.sort;

public class ParallelIntQuickSortTest extends ParallelIntSortTest {

  @Override
  public IntSortStrategy getSort(int concurrencyThreshold) {
    return new ParallelIntQuickSort(concurrencyThreshold);
  }
}
