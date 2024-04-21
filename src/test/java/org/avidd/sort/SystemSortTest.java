package org.avidd.sort;

import java.util.Arrays;
import java.util.Comparator;

public class SystemSortTest extends AbstractSortTest {

  @Override
  public void sort(String[] a, Comparator<String> comparator) {
    Arrays.sort(a, comparator);
  }
}
