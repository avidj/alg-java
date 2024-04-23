package org.avidd.graph;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

class Bfs implements Traversal {
  private final boolean[] marked;
  private final int[] edgeTo;
  private final int s;

  public Bfs(GenericGraph g, int aS) {
    s = aS;
    marked = new boolean[g.v()];
    edgeTo = new int[g.v()];
    traverse(g, s);
  }

  private void traverse(GenericGraph g, int s) {
    List<Integer> q = new LinkedList<>();
    q.add(s);
    marked[s] = true;
    while ( !q.isEmpty() ) {
      int v = q.remove(0);
      for ( int w : g.adj(v) ) {
        if ( !marked[w] ) {
          marked[w] = true;
          edgeTo[w] = v;
          q.add(w);
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
    Stack<Integer> path = new Stack<>();
    path.push(v);
    for ( ; v != s; v = edgeTo[v] )
      path.push(v);
    Collections.reverse(path);
    return Collections.unmodifiableList(path);
  }

}
