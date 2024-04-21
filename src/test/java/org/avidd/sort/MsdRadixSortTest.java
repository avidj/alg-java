package org.avidd.sort;

public class MsdRadixSortTest extends AbstractRadixSortTest {

  @Override
  public RadixSortStrategy getSort() {
    return new MsdRadixSort(); // too slow on input data
    // return null;
  }
}
