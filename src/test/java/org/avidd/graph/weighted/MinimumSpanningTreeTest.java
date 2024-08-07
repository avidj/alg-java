package org.avidd.graph.weighted;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

public class MinimumSpanningTreeTest {

  @Test
  public void testMstKruskal() {
    testMst(new MstKruskal());
  }

  @Test
  public void testMstPrim() {
    testMst(new MstPrim());
  }

  @Test
  public void testMstPrimEager() {
    testMst(new MstPrimEager());
  }

  public void testMst(MstStrategy mstStrategy) {
    final EdgeWeightedGraph graph = new EdgeWeightedGraph().edge(4, 5, 35).edge(4, 7, 37)
        .edge(5, 7, 28).edge(0, 7, 16).edge(1, 5, 32).edge(0, 4, 38).edge(2, 3, 17).edge(1, 7, 19)
        .edge(0, 2, 26).edge(1, 2, 36).edge(1, 3, 29).edge(2, 7, 34).edge(6, 2, 40).edge(3, 6, 52)
        .edge(6, 0, 58).edge(6, 4, 93);
    EdgeWeightedGraph mst = mstStrategy.mst(graph);
    System.out.println(mst);
    MatcherAssert.assertThat(mst.v(), is(equalTo(graph.v())));
    MatcherAssert.assertThat(costs(mst), is(equalTo(181)));

    EdgeWeightedGraph expected = new EdgeWeightedGraph().edge(0, 2, 26).edge(0, 7, 16)
        .edge(1, 7, 19).edge(2, 3, 17).edge(4, 5, 35).edge(5, 7, 28).edge(6, 2, 40);
    MatcherAssert.assertThat(mst, is(equalTo(expected)));
  }

  private double costs(EdgeWeightedGraph mst) {
    double costs = 0;
    for ( Edge edge : mst.edges() ) {
      costs += edge.weight();
    }
    return costs;
  }

}
