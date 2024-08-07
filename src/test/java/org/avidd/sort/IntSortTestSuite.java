package org.avidd.sort;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
   IntQuickSortTest.class,
   IntMergeSortTest.class,
   IntSystemSortTest.class
})
public class IntSortTestSuite {

}
