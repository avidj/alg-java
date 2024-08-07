package org.avidd.maps;

import org.junit.jupiter.api.Disabled;

@Disabled // not yet implemented
public class PatriciaTreeTest extends CharSeqMapTest {
  @Override
  PatriciaTree<Integer> newSymbolTable() {
    return new PatriciaTree<>();
  }

}
