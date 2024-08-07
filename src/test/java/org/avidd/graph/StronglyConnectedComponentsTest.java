package org.avidd.graph;

import static org.hamcrest.CoreMatchers.is;

import org.avidd.graph.directed.BlockingStronglyConnectedComponents;
import org.avidd.graph.directed.Digraph;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class StronglyConnectedComponentsTest {

  @Test
  public void testStronglyConnectedComponents() {
    final Digraph graph = new Digraph().edge(0, 1).edge(0, 5).edge(2, 0).edge(2, 3).edge(3, 2)
        .edge(3, 5).edge(4, 2).edge(4, 3).edge(5, 4).edge(6, 0).edge(6, 4).edge(6, 8).edge(6, 9)
        .edge(7, 6).edge(7, 9).edge(8, 6).edge(9, 10).edge(9, 11).edge(10, 12).edge(11, 4)
        .edge(11, 12).edge(12, 9);
    ConnectedComponents cc = new BlockingStronglyConnectedComponents(graph);
    MatcherAssert.assertThat(cc.connected(1, 0), is(false));
    MatcherAssert.assertThat(cc.connected(0, 2), is(true));
    MatcherAssert.assertThat(cc.connected(2, 3), is(true));
    MatcherAssert.assertThat(cc.connected(3, 4), is(true));
    MatcherAssert.assertThat(cc.connected(4, 5), is(true));
    MatcherAssert.assertThat(cc.connected(5, 0), is(true));

    MatcherAssert.assertThat(cc.connected(4, 9), is(false));
    MatcherAssert.assertThat(cc.connected(9, 10), is(true));
    MatcherAssert.assertThat(cc.connected(10, 11), is(true));
    MatcherAssert.assertThat(cc.connected(11, 12), is(true));
    MatcherAssert.assertThat(cc.connected(12, 9), is(true));

    MatcherAssert.assertThat(cc.connected(10, 6), is(false));
    MatcherAssert.assertThat(cc.connected(6, 8), is(true));
    MatcherAssert.assertThat(cc.connected(8, 6), is(true));

    MatcherAssert.assertThat(cc.connected(6, 7), is(false));
    System.out.println(cc);
  }

}
