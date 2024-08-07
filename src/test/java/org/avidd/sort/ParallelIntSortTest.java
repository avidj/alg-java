package org.avidd.sort;

import static org.avidd.sort.SortUtil.assertSorted;
import org.junit.jupiter.api.Test;

public abstract class ParallelIntSortTest {

  public abstract IntSortStrategy getSort(int concurrencyThreshold);

  private void testSort(int[] a, int threshold) {
    IntSortStrategy strategy = getSort(threshold);
    final long then = System.currentTimeMillis();
    strategy.sort(a);
    final long now = System.currentTimeMillis();
    System.out
        .println(new StringBuilder().append(strategy.getClass().getSimpleName()).append("\t")
            .append(a.length).append("\t").append(threshold).append("\t").append(now - then)
            .toString());
    assertSorted(a);
  }

  @Test
  public void testSort1K_100() {
    testSort(UnsortedArrays.unsorted1K(), 100);
  }

  @Test
  public void testSort10K_100() {
    testSort(UnsortedArrays.unsorted10K(), 100);
  }

  @Test
  public void testSort100K_100() {
    testSort(UnsortedArrays.unsorted100K(), 100);
  }

  @Test
  public void testSort100K_1K() {
    testSort(UnsortedArrays.unsorted100K(), 1000);
  }

  @Test
  public void testSort100K_10K() {
    testSort(UnsortedArrays.unsorted100K(), 10000);
  }

  @Test
  public void testSort1M_100() {
    testSort(UnsortedArrays.unsorted1M(), 100);
  }

  @Test
  public void testSort1M_1K() {
    testSort(UnsortedArrays.unsorted1M(), 1000);
  }

  @Test
  public void testSort1M_10K() {
    testSort(UnsortedArrays.unsorted1M(), 10000);
  }

  @Test
  public void testSort1M_100K() {
    testSort(UnsortedArrays.unsorted1M(), 100000);
  }

  // @Test public void testSort10M_100() { testSort(UNSORTED_10M, 100); }
  //
  // @Test public void testSort10M_1K() { testSort(UNSORTED_10M, 1000); }
  //
  // @Test public void testSort10M_10K() { testSort(UNSORTED_10M, 10000); }
  //
  // @Test public void testSort10M_100K() { testSort(UNSORTED_10M, 100000); }
  //
  // @Test public void testSort10M_1M() { testSort(UNSORTED_10M, 1000000); }

  // @Test public void testSort100M_100() { testSort(UNSORTED_100M, 100); }
  //
  // @Test public void testSort100M_1K() { testSort(UNSORTED_100M, 1000); }
  //
  // @Test public void testSort100M_10K() { testSort(UNSORTED_100M, 10000); }
  //
  // @Test public void testSort100M_100K() { testSort(UNSORTED_100M, 100000); }
  //
  // @Test public void testSort100M_1M() { testSort(UNSORTED_100M, 1000000); }
  //
  // @Test public void testSort100M_10M() { testSort(UNSORTED_100M, 10000000); }
}
