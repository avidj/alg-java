package org.avidd.sort;

import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractRadixSortTest {

  public abstract RadixSortStrategy getSort();

  private void testSort(String[] toSort) {
    String[] a = new String[toSort.length];
    System.arraycopy(toSort, 0, a, 0, a.length);
    RadixSortStrategy strategy = getSort();
    final long then = System.currentTimeMillis();
    strategy.sort(a);
    final long now = System.currentTimeMillis();
    System.out.println(
        new StringBuilder()
        .append(strategy.getClass().getSimpleName())
        .append("\t")
        .append(toSort.length)
        .append("\t")
        .append(now - then)
        .toString());
    assertSorted(a);
    System.out.println(toString(a, 0, 10));
  }

  private static String toString(String[] a, int i, int j) {
    assert ( i <= j );
    StringBuilder string = new StringBuilder("[");    
    string.append(a[i]);
    for ( int k = i + 1, n = Math.min(j, a.length - 1); k <= n; k++ ) {
      string.append(", ").append(a[k]);
    }
    return string.append("]").toString();
  }

  @Test
  public void testSort3() {
    testSort(UnsortedArrays.unsorted3String());
  }

  @Test
  public void testSort1K() {
    testSort(UnsortedArrays.unsorted1KString());
  }

  @Test
  public void testSort10K() {
    testSort(UnsortedArrays.unsorted10KString());
  }

  @Test
  public void testSort100K() {
    testSort(UnsortedArrays.unsorted100KString());
  }

  @Test
  public void testSort1M() {
    testSort(UnsortedArrays.unsorted1MString());
  }

  // @Test public void testSort10M() { testSort(UNSORTED_10M); }

  // @Test public void testSort100M() { testSort(UNSORTED_100M); }

  private static void assertSorted(String[] a) {
    for ( int i = 0; i < a.length - 2; i++ ) {
      Assert.assertTrue(a[i].compareTo(a[i + 1]) <= 0);
    }
  }

}
