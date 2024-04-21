package org.avidd.sort;

public class IntShakerSortTest extends AbstractIntSortTest {
  @Override
  public void sort(int[] a) {
    new IntShakerSort().sort(a);
  }
}
