package org.avidd.graph;

import java.util.List;

import org.avidd.graph.weighted.Edge;

/**
 * Interface for single-source shortest paths algorithms.  
 */
public interface ShortestPaths {

  /**
   * @param v the node to query
   * @return the distance to the node from the origin
   */
  public double distTo(int v);

  /**
   * @param v the node to query
   * @return the path to the node
   */
  public List<Edge> pathTo(int v);

}
