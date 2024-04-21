package org.avidd.sort;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  ParallelQuickSortTest.class,
  ParallelThreeWayQuickSortTest.class,
  ParallelMergeSortTest.class,
  ParallelOptimalMergeSortTest.class
})
public class ParallelSortTestSuite {

}
