package org.avidd.sort;

import java.util.Comparator;

public class HeapSortTest extends AbstractSortTest {
  @Override
  public void sort(String[] a, Comparator<String> comparator) {
    new HeapSort().sort(a, comparator);
  }
}
