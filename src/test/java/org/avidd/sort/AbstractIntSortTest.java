package org.avidd.sort;

import static org.avidd.sort.SortUtil.assertSorted;
import org.junit.jupiter.api.Test;

public abstract class AbstractIntSortTest {

  public abstract void sort(int[] a);

  private void testSort(int[] a) {
    final long then = System.currentTimeMillis();
    sort(a);
    final long now = System.currentTimeMillis();
    System.out.println(new StringBuilder().append(getClass().getSimpleName()).append("\t")
        .append(a.length).append("\t").append(now - then).toString());
    assertSorted(a);
  }

  @Test
  public void testSortEmpty() {
    testSort(new int[0]);
  }

  @Test
  public void testSort1K() {
    testSort(UnsortedArrays.unsorted1K());
  }

  @Test
  public void testSort10K() {
    testSort(UnsortedArrays.unsorted10K());
  }

  @Test
  public void testSort100K() {
    testSort(UnsortedArrays.unsorted100K());
  }

  // @Test public void testSort1M() { testSort(UNSORTED_1M); }
  //
  // @Test public void testSort10M() { testSort(UNSORTED_10M); }

  // @Test public void testSort100M() { testSort(UNSORTED_100M); }

  
}
