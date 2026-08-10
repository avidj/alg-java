package org.avidd.maps;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RWayTrieTest extends CharSeqMapTest {

  @Override
  StringSymbolTable<Integer> newSymbolTable() {
    return new RWayTrie<>();
  }

  @Override
  @Test
  @Disabled("RWayTrie.min() is an unimplemented stub - see TODO.md")
  public void testMin() {
    super.testMin();
  }

  @Override
  @Test
  @Disabled("RWayTrie.max() is an unimplemented stub - see TODO.md")
  public void testMax() {
    super.testMax();
  }

  @Override
  @Test
  @Disabled("RWayTrie.floor() is an unimplemented stub - see TODO.md")
  public void testFloor() {
    super.testFloor();
  }

  @Override
  @Test
  @Disabled("RWayTrie.ceiling() is an unimplemented stub - see TODO.md")
  public void testCeiling() {
    super.testCeiling();
  }
}
