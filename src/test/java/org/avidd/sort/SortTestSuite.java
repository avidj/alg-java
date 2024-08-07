package org.avidd.sort;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
  SystemSortTest.class,
  MergeSortTest.class,
  OptimalMergeSortTest.class,
  QuickSortTest.class,
  ThreeWayQuickSortTest.class
})
public class SortTestSuite {

}
