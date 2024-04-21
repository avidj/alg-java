package org.avidd.sort;

import static org.avidd.sort.SortUtil.assertSorted;

import java.util.Comparator;

import org.junit.Test;

public abstract class AbstractSortTest {

  public abstract void sort(String[] a, Comparator<String> comparator);

  private void testSort(String[] a) {
    final long then = System.currentTimeMillis();
    sort(a, SortUtil.LEX_ORDER);
    final long now = System.currentTimeMillis();
    System.out.println(new StringBuilder().append(getClass().getSimpleName()).append("\t")
        .append(a.length).append("\t").append(now - then).toString());
    assertSorted(a);
  }

  @Test
  public void testSortEmpty() {
    testSort(new String[0]);
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
  public void testUlysses() {
    testSort(UnsortedArrays.ulysses());
  }

  @Test
  public void testLeonardoDaVinci() {
    testSort(UnsortedArrays.leonardoDaVinci());
  }

  @Test
  public void testOutlineOfScienceK() {
    testSort(UnsortedArrays.outlineOfScience());
  }

  @Test
  public void testSort1M() {
    testSort(UnsortedArrays.unsorted1MString());
  }

//  @Test
//  public void testSort10M() {
//    testSort(UnsortedArrays.unsorted10MString());
//  }
}
