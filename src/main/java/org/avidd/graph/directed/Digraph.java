package org.avidd.graph.directed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.avidd.graph.GenericGraph;

/**
 * A directed graph represented as a list of adjacency lists.
 * 
 * @author david
 */
public class Digraph implements GenericGraph {
  // Storing adjacency lists allows for parallel edges.
  private final List<List<Integer>> adj = new ArrayList<>();

  /**
   * Create a new edge.
   * 
   * @param u
   *          the origin of the edge
   * @param v
   *          the destination of the edge
   * @return this graph for convenience
   */
  public Digraph edge(int u, int v) {
    int n = Math.max(u, v);
    for ( int i = adj.size(); i <= n; i++ ) {
      adj.add(Collections.<Integer> emptyList());
    }
    List<Integer> uAdj = adj.get(u);
    if ( uAdj.isEmpty() ) {
      uAdj = new LinkedList<>();
      adj.set(u, uAdj);
    }
    uAdj.add(v);
    return this;
  }

  /**
   * Create a graph that has the same edges but reversed.
   * 
   * @return the reversed digraph
   */
  public Digraph reverse() {
    Digraph reverse = new Digraph();
    for ( int v = 0, n = adj.size(); v < n; v++ ) {
      for ( Integer w : adj(v) ) {
        reverse.edge(w, v);
      }
    }
    return reverse;
  }

  @Override
  public int v() {
    return adj.size();
  }

  @Override
  public int e() {
    int edges = 0;
    for ( List<Integer> list : adj ) {
      edges += list.size();
    }
    return edges;
  }

  @Override
  public Collection<Integer> adj(int v) {
    return Collections.unmodifiableList(adj.get(v));
  }
}
