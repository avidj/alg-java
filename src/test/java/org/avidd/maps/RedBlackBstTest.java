package org.avidd.maps;

import org.avidd.util.ComparableComparator;

public class RedBlackBstTest extends SymbolTableTest<LLRedBlackBst<String, Integer>> {

  @Override
  LLRedBlackBst<String, Integer> newSymbolTable() {
    return new LLRedBlackBst<String, Integer>(new ComparableComparator<String>());
  }
}
