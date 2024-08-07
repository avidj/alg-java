package org.avidd.sort;

import org.junit.jupiter.api.Test;

public abstract class ParallelSortTest {

  public abstract SortStrategy getSort(int concurrencyThreshold);

  private void testSort(String[] a, int threshold) {
    SortStrategy strategy = getSort(threshold);
    final long then = System.currentTimeMillis();
    strategy.sort(a, SortUtil.LEX_ORDER);
    final long now = System.currentTimeMillis();
    System.out
        .println(new StringBuilder().append(strategy.getClass().getSimpleName()).append("\t")
            .append(a.length).append("\t").append(threshold).append("\t").append(now - then)
            .toString());
    SortUtil.assertSorted(a);
  }

  @Test
  public void testSortLeonardoDaVinci1K() {
    testSort(UnsortedArrays.leonardoDaVinci(), 1000);
  }

  @Test
  public void testSortOutlineOfScience1K() {
    testSort(UnsortedArrays.outlineOfScience(), 1000);
  }

  @Test
  public void testSortUlysses1K() {
    testSort(UnsortedArrays.ulysses(), 1000);
  }

  @Test
  public void testSort1K_100() {
    testSort(UnsortedArrays.unsorted1KString(), 100);
  }

  @Test
  public void testSort10K_100() {
    testSort(UnsortedArrays.unsorted10KString(), 100);
  }

  @Test
  public void testSort100K_100() {
    testSort(UnsortedArrays.unsorted100KString(), 100);
  }

  @Test
  public void testSort100K_1K() {
    testSort(UnsortedArrays.unsorted100KString(), 1000);
  }

  @Test
  public void testSort100K_10K() {
    testSort(UnsortedArrays.unsorted100KString(), 10000);
  }

  @Test
  public void testSort1M_100() {
    testSort(UnsortedArrays.unsorted1MString(), 100);
  }

  @Test
  public void testSort1M_1K() {
    testSort(UnsortedArrays.unsorted1MString(), 1000);
  }

//  @Test
//  public void testSort1M_10K() {
//    testSort(UnsortedArrays.unsorted1MString(), 10000);
//  }

//  @Test
//  public void testSort1M_100K() {
//    testSort(UnsortedArrays.unsorted1MString(), 100000);
//  }

//  @Test
//  public void testSort10M_1K() {
//    testSort(UnsortedArrays.unsorted10MString(), 1000);
//  }

//  @Test
//  public void testSort100M_1K() {
//    testSort(UnsortedArrays.unsorted100MString(), 1000);
//  }

//  @Test
//  public void testSort1B_1K() {
//    testSort(UnsortedArrays.unsorted1BString(), 1000);
//  }

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
