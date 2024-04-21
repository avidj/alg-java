package org.avidd.unionfind;

public class WeightedQuickUnionPathCompressionTest extends UnionFindTest {

  @Override
  UnionFind getStrategy(int n) {
    return new WeightedQuickUnionPathCompression(n);
  }
}
