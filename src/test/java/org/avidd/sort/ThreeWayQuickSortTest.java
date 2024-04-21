package org.avidd.sort;

import java.util.Comparator;

public class ThreeWayQuickSortTest extends AbstractSortTest {

  @Override
  public void sort(String[] a, Comparator<String> comparator) {
    new ThreeWayQuickSort().sort(a, comparator);
  }
}
