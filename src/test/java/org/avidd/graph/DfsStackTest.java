package org.avidd.graph;

import org.junit.Test;

public class DfsStackTest {

  @Test
  public void testDfs() {
    final Graph graph = new Graph().edge(0, 1).edge(0, 2).edge(0, 5).edge(1, 2).edge(2, 3)
        .edge(2, 4).edge(3, 4).edge(3, 5);
    Traversal dfs = new DfsStack(graph, 0);
    System.out.println(dfs);
  }

}
