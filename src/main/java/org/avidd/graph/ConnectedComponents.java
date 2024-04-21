package org.avidd.graph;

/**
 * Interface for algorithms computing connected components in an undirected graph.
 * 
 * @author David Kensche
 */
public interface ConnectedComponents {

  /**
   * @param v the node to query the id for
   * @return the id of the component that the node belongs to
   */
  public abstract int id(int v);

  /**
   * @return the number of connected components in the graph
   */
  public abstract int count();

  /**
   * @param v a node
   * @param w another node
   * @return true, iff both nodes are in the same component
   */
  public abstract boolean connected(int v, int w);

}
