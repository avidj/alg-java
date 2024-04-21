package org.avidd.sort;

public class IntInsertionSortTest extends AbstractIntSortTest {
  @Override
  public void sort(int[] a) {
    new IntInsertionSort().sort(a);
  }
}
