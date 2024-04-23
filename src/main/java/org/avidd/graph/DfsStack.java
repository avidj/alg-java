package org.avidd.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/** 
 * Depth first search using an explicit stack.
 * Marks all vertices connected to s in time proportional to {@link org.avidd.graph.GenericGraph#e()}.
 */
class DfsStack implements Traversal {
  private final int s;
  private final int[] edgeTo;
  private final boolean[] marked;

  DfsStack(GenericGraph g, int start) {
    s = start;
    edgeTo = new int[g.v()];
    marked = new boolean[g.v()];
    traverse(g, s);
  }

  private void traverse(GenericGraph g, int s) {
    Stack<Integer> stack = new Stack<>();
    stack.push(s);
    marked[s] = true;
    while ( !stack.isEmpty() ) {
      int v = stack.pop();
      for ( int w : g.adj(v) ) {
        if ( !marked[w] ) {
          marked[w] = true;
          edgeTo[w] = v;
          stack.push(w);
        }
      }
    }
  }

  @Override
  public boolean hasPathTo(int v) {
    return marked[v];
  }

  @Override
  public List<Integer> pathTo(int v) {
    List<Integer> path = new LinkedList<>();
    path.add(0, v);
    for ( ; v != s; v = edgeTo[v] )
      path.add(0, v);
    return Collections.unmodifiableList(path);
  }

}
