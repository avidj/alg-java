package org.avidd.unionfind;

public class QuickFindTest extends UnionFindTest {

  @Override
  UnionFind getStrategy(int n) {
    return new QuickFind(n);
  }
}
