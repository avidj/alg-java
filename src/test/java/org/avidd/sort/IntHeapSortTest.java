package org.avidd.sort;

public class IntHeapSortTest extends AbstractIntSortTest {
  @Override
  public void sort(int[] a) {
    new IntHeapSort().sort(a);
  }
}
