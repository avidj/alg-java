package org.avidd.unionfind;

public class WeightedQuickUnionTest extends UnionFindTest {

  @Override
  UnionFind getStrategy(int n) {
    return new WeightedQuickUnion(n);
  }
}
