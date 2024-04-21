package org.avidd.graph.directed;

import java.util.List;

/**
 * Interface for algorithms that compute topological orders in graphs and
 * detected directed cycles. These two problems are related as a digraph has a
 * topological order if and only if it has no cycle.
 * 
 * @author David Kensche
 */
public interface TopologicalSort {

  /**
   * @return true, iff the graph has a cycle, false if it has a topological
   *         order
   */
  public boolean hasCycle();

  /**
   * @return an immutable list representing the cycle in the graph
   * @throws IllegalStateException
   *           if the graph has no cycle, i.e., if {@link #hasCycle()} is false
   */
  public List<Integer> getCycle() throws IllegalStateException;

  /**
   * @return an immutable list representing a topological of the graph
   * @throws IllegalStateException
   *           if the graph has a cycle, i.e., if {@link #hasCycle()} is true
   */
  public List<Integer> getTopologicalOrder();

}
