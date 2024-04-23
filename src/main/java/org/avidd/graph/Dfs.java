package org.avidd.graph;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/** 
 * Depth first search using the call stack.
 * Marks all vertices connected to s in time proportional to {@link org.avidd.graph.GenericGraph#e()}.
 */
class Dfs implements Traversal {
  private final boolean[] marked;
  private final int[] edgeTo;
  private final int s;

  public Dfs(GenericGraph g, int aS) {
    s = aS;
    marked = new boolean[g.v()];
    edgeTo = new int[g.v()];
    traverse(g, s);
  }

  /**
   * Recursive depth first search.
   * @param g the graph to search
   * @param v the node to start DFS at
   */
  private void traverse(GenericGraph g, int v) {
    marked[v] = true;
    for ( int w : g.adj(v) ) {
      if ( !marked[w] ) {
        traverse(g, w);
        edgeTo[w] = v;
      }
    }
  }

  @Override
  public boolean hasPathTo(int v) {
    return marked[v];
  }

  @Override
  public List<Integer> pathTo(int v) {
    if ( !hasPathTo(v) ) {
      return Collections.emptyList();
    }
    Stack<Integer> path = new Stack<>();
    for ( int x = v; x != s; x = edgeTo[x] ) {
      path.push(edgeTo[x]);
    }
    Collections.reverse(path);
    return Collections.unmodifiableList(path);
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder("Dfs( edgeTo = { ");
    if ( edgeTo.length == 0 ) {
      return string.append("} )").toString();
    }
    string.append(0).append(" : ").append(edgeTo[0]);
    for ( int i = 1; i < edgeTo.length; i++ ) {
      string.append(", ").append(i).append(" : ").append(edgeTo[i]);
    }
    return string.append(" } )").toString();
  }
}
