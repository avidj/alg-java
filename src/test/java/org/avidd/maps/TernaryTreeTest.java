package org.avidd.maps;

import org.junit.jupiter.api.Disabled;

@Disabled // not yet implemented 
public class TernaryTreeTest extends CharSeqMapTest {
  @Override
  StringSymbolTable<Integer> newSymbolTable() {
    return new TernaryTree<>();
  }
}
