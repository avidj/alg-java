package org.avidd.maps;

public class TernaryTreeTest extends CharSeqMapTest {
  @Override
  StringSymbolTable<Integer> newSymbolTable() {
    return new TernaryTree<Integer>();
  }
}
