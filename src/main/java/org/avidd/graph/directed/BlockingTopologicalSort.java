package org.avidd.graph.directed;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * A topological sort that the constructor of which blocks until the algorithm is finished.
 *  
 * @author David Kensche
 */
public class BlockingTopologicalSort implements TopologicalSort {
  private final boolean[] marked;
  private final int[] edgeTo;
  private final boolean[] onStack;
  private List<Integer> cycle;
  private final List<Integer> reversePost;

  public BlockingTopologicalSort(Digraph g) {
    marked = new boolean[g.v()];
    edgeTo = new int[g.v()];
    onStack = new boolean[g.v()];
    reversePost = new Stack<Integer>();
    for ( int v = 0; v < g.v(); v++ ) {
      if ( !marked[v] ) {
        traverse(g, v);
      }
    }
  }

  private void traverse(Digraph g, int v) {
    marked[v] = true;
    onStack[v] = true;
    for ( int w : g.adj(v) ) {
      if ( cycle != null )
        return;
      if ( !marked[w] ) {
        onStack[w] = true;
        edgeTo[w] = v;
        traverse(g, w);
      } else if ( onStack[w] ) {
        cycle = new Stack<Integer>();
        for ( int x = v; x != w; x = edgeTo[x] )
          cycle.add(0, x);
        cycle.add(0, w);
        cycle.add(0, v);
      }
    }
    onStack[v] = false;
    reversePost.add(0, v);
  }

  public boolean hasCycle() {
    return ( cycle != null );
  }

  public List<Integer> getCycle() {
    return Collections.unmodifiableList(cycle);
  }

  public List<Integer> getTopologicalOrder() {
    return Collections.unmodifiableList(reversePost);
  }
}
