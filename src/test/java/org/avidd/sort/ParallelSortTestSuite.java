package org.avidd.sort;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
  ParallelQuickSortTest.class,
  ParallelThreeWayQuickSortTest.class,
  ParallelMergeSortTest.class,
  ParallelOptimalMergeSortTest.class
})
public class ParallelSortTestSuite {

}
