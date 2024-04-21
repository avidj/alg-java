package org.avidd.sort;

import java.util.Comparator;

public class MergeSortTest extends AbstractSortTest {
  @Override
  public void sort(String[] a, Comparator<String> comparator) {
    new MergeSort().sort(a, comparator);
  }
}
