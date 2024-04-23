package org.avidd.graph.weighted;

/**
 * A strategy for computing minimum spanning trees. 
 */
public interface MstStrategy {

  /**
   * @param graph the edge weighted graph to compute the MST for
   * @return the MST which is basically a sub graph of the input
   */
  public EdgeWeightedGraph mst(EdgeWeightedGraph graph);

}
