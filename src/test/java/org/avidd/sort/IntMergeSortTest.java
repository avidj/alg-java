package org.avidd.sort;

public class IntMergeSortTest extends AbstractIntSortTest {
  @Override
  public void sort(int[] a) {
    new IntMergeSort().sort(a);
  }
}
