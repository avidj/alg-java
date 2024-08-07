package org.avidd.graph;

import org.junit.jupiter.api.Test;

public class ConnectedComponentsTest {

  @Test
  public void testDfs() {
    final Graph graph = new Graph().edge(0, 1).edge(0, 2).edge(0, 6).edge(3, 4).edge(3, 5)
        .edge(4, 5).edge(4, 6).edge(7, 8).edge(9, 10).edge(9, 11).edge(9, 12).edge(11, 12);
    ConnectedComponents cc = new BlockingConnectedComponents(graph);
    System.out.println(cc);
  }

}
