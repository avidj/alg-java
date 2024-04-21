package org.avidd.graph.directed;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class FutureTopologicalSort implements TopologicalSort {
  private final Future<Boolean> topOrderFuture;
  private final TSCallable ts;

  public FutureTopologicalSort(Digraph g, ExecutorService executor) {
    ts = new TSCallable(g);
    topOrderFuture = executor.submit(ts);
  }

  @Override
  public boolean hasCycle() {
    try {
      return topOrderFuture.get();
    } catch ( Exception e ) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Integer> getTopologicalOrder() {
    try {
      boolean hasCycle = topOrderFuture.get();
      if ( hasCycle )
        throw new IllegalStateException("The graph has a cycle.");
      return ts.getTopologicalOrder();
    } catch ( Exception e ) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Integer> getCycle() {
    try {
      boolean hasCycle = topOrderFuture.get();
      if ( !hasCycle )
        throw new IllegalStateException("The graph has no cycle.");
      return ts.getCycle();
    } catch ( Exception e ) {
      throw new RuntimeException(e);
    }
  }

  private static class TSCallable implements Callable<Boolean> {
    private final Digraph g;
    private final boolean[] marked;
    private final int[] edgeTo;
    private final boolean[] onStack;
    private List<Integer> cycle;
    private final List<Integer> topOrder;

    public TSCallable(Digraph graph) {
      g = graph;
      marked = new boolean[graph.v()];
      edgeTo = new int[g.v()];
      onStack = new boolean[g.v()];
      topOrder = new LinkedList<Integer>();
    }

    @Override
    public Boolean call() throws Exception {
      topSort();
      return hasCycle();
    }

    private void topSort() {
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
      topOrder.add(0, v);
    }

    public boolean hasCycle() {
      return cycle != null;
    }

    private List<Integer> getCycle() {
      if ( !hasCycle() ) {
        throw new IllegalStateException("The graph has no cycle.");
      }
      return Collections.unmodifiableList(cycle);
    }

    private List<Integer> getTopologicalOrder() {
      if ( hasCycle() ) {
        throw new IllegalStateException("The graph has a cycle.");
      }
      return Collections.unmodifiableList(topOrder);
    }
  }
}
