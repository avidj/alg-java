package org.avidd.sort;

import java.util.Comparator;


public class OptimalMergeSortTest extends AbstractSortTest {
  @Override
  public void sort(String[] a, Comparator<String> comparator) {
    new OptimalMergeSort().sort(a, comparator);
  }
}
