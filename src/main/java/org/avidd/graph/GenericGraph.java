package org.avidd.graph;

/**
 * Interface of all graphs, directed or not.
 * 
 * @author David Kensche
 */
public interface GenericGraph {

  /**
   * @return the number of nodes in this graph
   */
  public int v();

  /**
   * @return the number of edges in this graph
   */
  public int e();

  /**
   * @param v
   *          the node the adjacent nodes of which are to iterate
   * @return an iterator over the nodes adjacent to the input node v
   */
  public Iterable<Integer> adj(int v);

}
