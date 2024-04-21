package org.avidd.sort;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  SystemSortTest.class,
  MergeSortTest.class,
  OptimalMergeSortTest.class,
  QuickSortTest.class,
  ThreeWayQuickSortTest.class
})
public class SortTestSuite {

}
