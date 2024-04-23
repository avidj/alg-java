package org.avidd.maps;

import org.junit.Ignore;

@Ignore // Not yet implemented
public class RWayTrieTest extends CharSeqMapTest {

  @Override
  StringSymbolTable<Integer> newSymbolTable() {
    return new RWayTrie<>();
  }
}
