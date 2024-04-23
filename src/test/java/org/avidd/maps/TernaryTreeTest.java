package org.avidd.maps;

import org.junit.Ignore;

@Ignore // not yet implemented 
public class TernaryTreeTest extends CharSeqMapTest {
  @Override
  StringSymbolTable<Integer> newSymbolTable() {
    return new TernaryTree<>();
  }
}
