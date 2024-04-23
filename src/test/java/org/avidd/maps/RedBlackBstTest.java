package org.avidd.maps;

import org.avidd.util.ComparableComparator;
import org.junit.Ignore;

@Ignore //not implemented
public class RedBlackBstTest extends SymbolTableTest<LLRedBlackBst<String, Integer>> {

  @Override
  LLRedBlackBst<String, Integer> newSymbolTable() {
    return new LLRedBlackBst<>(new ComparableComparator<String>());
  }
}
