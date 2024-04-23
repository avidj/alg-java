package org.avidd.maps;

import org.junit.Ignore;

@Ignore // not yet implemented
public class PatriciaTreeTest extends CharSeqMapTest {
  @Override
  PatriciaTree<Integer> newSymbolTable() {
    return new PatriciaTree<>();
  }

}
