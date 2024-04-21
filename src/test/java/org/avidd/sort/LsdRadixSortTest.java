package org.avidd.sort;

public class LsdRadixSortTest extends AbstractRadixSortTest {

  @Override
  public RadixSortStrategy getSort() {
    return new LsdRadixSort();
  }
}
