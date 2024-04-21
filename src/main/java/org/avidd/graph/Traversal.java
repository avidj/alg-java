package org.avidd.graph;

import java.util.List;

/**
 * Interface of graph traversals.
 * 
 * @author David Kensche
 */
public interface Traversal {

  /**
   * @param v the node to query
   * @return true, iff the traversal found a path to the node
   */
  public boolean hasPathTo(int v);

  /**
   * @param v the node to query the path to
   * @return an immutable path to the node
   */
  public List<Integer> pathTo(int v);

}
