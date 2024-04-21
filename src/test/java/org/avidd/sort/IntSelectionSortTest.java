package org.avidd.sort;

public class IntSelectionSortTest extends AbstractIntSortTest {
  @Override
  public void sort(int[] a) {
    new IntSelectionSort().sort(a);
  }
}
