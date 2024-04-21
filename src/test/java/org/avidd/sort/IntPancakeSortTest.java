package org.avidd.sort;

import static org.avidd.sort.SortUtil.assertSorted;

import org.junit.Test;

public class IntPancakeSortTest {

  public IntSortStrategy getSort() { return new IntPancakeSort(); }

  private void testSort(int[] a) {
    IntSortStrategy strategy = getSort();
    final long then = System.currentTimeMillis();
    strategy.sort(a);
    final long now = System.currentTimeMillis();
    System.out.println(new StringBuilder().append(strategy.getClass().getSimpleName()).append("\t")
        .append(a.length).append("\t").append(now - then).toString());
    assertSorted(a);
  }

//  @Test
//  public void testSortRandom() {
//    testSort(UnsortedArrays.genNumbers(25, 50));
//  }

  @Test
  public void testSort() {
    testSort(new int[] { 8, 6, 10, 4, 2 });
  }

  @Test
  public void testSort2() {
    testSort(new int[] { 2, 3, 5, 4, 1 });
  }
}
