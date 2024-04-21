package org.avidd.graph.directed;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class PureTopologicalSort implements TopologicalSort {
  private final boolean[] marked;
  private final List<Integer> topOrder;

  public PureTopologicalSort(Digraph g) {
    marked = new boolean[g.v()];
    topOrder = new Stack<Integer>();
    for ( int v = 0; v < g.v(); v++ ) {
      if ( !marked[v] ) {
        traverse(g, v);
      }
    }
  }

  private void traverse(Digraph g, int v) {
    marked[v] = true;
    for ( int w : g.adj(v) ) {
      if ( !marked[w] )
        traverse(g, w);
    }
    topOrder.add(0, v);
  }

  public boolean hasCycle() {
    throw new UnsupportedOperationException();
  }

  public List<Integer> getCycle() {
    throw new UnsupportedOperationException();
  }

  public List<Integer> getTopologicalOrder() {
    return Collections.unmodifiableList(topOrder);
  }
}
