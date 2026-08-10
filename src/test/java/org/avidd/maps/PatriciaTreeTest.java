package org.avidd.maps;

import org.junit.jupiter.api.Disabled;

@Disabled("PatriciaTree is an unfinished sketch: put() is incomplete and the get() helper is an "
    + "empty stub, so no test can pass - see TODO.md")
public class PatriciaTreeTest extends CharSeqMapTest {
  @Override
  PatriciaTree<Integer> newSymbolTable() {
    return new PatriciaTree<>();
  }

}
