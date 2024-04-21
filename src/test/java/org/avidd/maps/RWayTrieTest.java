package org.avidd.maps;

public class RWayTrieTest extends CharSeqMapTest {

  @Override
  StringSymbolTable<Integer> newSymbolTable() {
    return new RWayTrie<Integer>();
  }
}
