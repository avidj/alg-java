package org.avidd.sort;

public class IntQuickSortTest extends AbstractIntSortTest {

  @Override
  public void sort(int[] a) {
    new IntQuickSort().sort(a);
  }
}
