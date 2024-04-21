package org.avidd.sort;

import java.util.Comparator;

public class QuickSortTest extends AbstractSortTest {

  @Override
  public void sort(String[] a, Comparator<String> comparator) {
    new QuickSort().sort(a, comparator);
  }
}
