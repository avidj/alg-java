package org.avidd.unionfind;

import static org.hamcrest.CoreMatchers.is;

import org.junit.Assert;
import org.junit.Test;

public abstract class UnionFindTest {

  abstract UnionFind getStrategy(int n);

  @Test
  public void testUnionFind10() {
    UnionFind uf = getStrategy(10);
    System.out.println(uf.toString());
    uf.union(4, 3);
    System.out.println(uf.toString());
    uf.union(3, 8);
    System.out.println(uf.toString());
    uf.union(6, 5);
    System.out.println(uf.toString());
    uf.union(9, 4);
    System.out.println(uf.toString());
    uf.union(2, 1);
    System.out.println(uf.toString());

    Assert.assertThat(uf.connected(0, 7), is(false));
    Assert.assertThat(uf.connected(8, 9), is(true));

    uf.union(5, 0);
    System.out.println(uf.toString());
    uf.union(7, 2);
    System.out.println(uf.toString());
    uf.union(6, 1);
    System.out.println(uf.toString());
    uf.union(1, 0);
    System.out.println(uf.toString());

    Assert.assertThat(uf.connected(0, 7), is(true));
  }
}
