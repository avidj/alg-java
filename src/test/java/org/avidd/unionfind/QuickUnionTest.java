package org.avidd.unionfind;

public class QuickUnionTest extends UnionFindTest {

  @Override
  UnionFind getStrategy(int n) {
    return new QuickUnion(n);
  }
}
