package org.avidd.graph;

/**
 * Utility class providing graph traversal strategies.
 * 
 * TODO: parallelize
 * TODO: is this useful?
 *  
 * @author David Kensche
 */
public final class Traversals {

  private Traversals() { /* private utility class constructor */ }

  /**
   * @param g the graph to traverse
   * @param s the start node for the traversal
   * @return a breadth first traversal
   */
  public static Traversal bfs(GenericGraph g, int s) {
    return new Bfs(g, s);
  }

  /**
   * @param g the graph to traverse
   * @param s the start node for the traversal
   * @return a depth first traversal using the call stack
   */
  public static Traversal dfs(GenericGraph g, int s) {
    return new Dfs(g, s);
  }

  /**
   * @param g the graph to traverse
   * @param s the start node for the traversal
   * @return a depth first traversal using an explicit stack
   */
  public static Traversal dfsStack(GenericGraph g, int s) {
    return new DfsStack(g, s);
  }
}
