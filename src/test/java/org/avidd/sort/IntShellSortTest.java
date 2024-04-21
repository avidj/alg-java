package org.avidd.sort;

public class IntShellSortTest extends AbstractIntSortTest {
  @Override
  public void sort(int[] a) {
    new IntShellSort().sort(a);
  }
}
