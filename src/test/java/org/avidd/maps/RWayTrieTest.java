package org.avidd.maps;

import org.junit.jupiter.api.Disabled;

@Disabled // Not yet implemented
public class RWayTrieTest extends CharSeqMapTest {

  @Override
  StringSymbolTable<Integer> newSymbolTable() {
    return new RWayTrie<>();
  }
}
