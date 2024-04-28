package org.avidd.graph.weighted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.avidd.graph.directed.Digraph;

/**
 * An edge weighted directed graph.
 */
public class EdgeWeightedDigraph extends Digraph {
  private static final int HASH_SHIFT = 31;
  private static final int HASH_SEED = 997;
  private final List<Set<Edge>> adj;

  /**
   * Create a new weighted graph represented as an edge list.
   */
  public EdgeWeightedDigraph() {
    adj = new ArrayList<>();
  }

  /**
   * Create a new edge in this graph and return this graph for convenience.
   * 
   * @param v the origin of the edge
   * @param w the destination of the edge
   * @param weight the weight of the edge
   * @return this graph, for convenient concatenation of edges
   */
  public EdgeWeightedDigraph edge(int v, int w, double weight) {
    int m = Math.max(v, w);
    for ( int i = adj.size(); i <= m; i++ ) {
      adj.add(new HashSet<>());
    }
    // create the edge
    Edge edge = Edge.edge(v, w, weight);
    adj.get(v).add(edge);
    return this;
  }

  /**
   * @return an iterable over the edges in this graph
   */
  public Set<Edge> edges() {
    Set<Edge> allEdges = new HashSet<>();
    for ( Set<Edge> edges : adj ) {
      allEdges.addAll(edges);
    }
    return Collections.unmodifiableSet(allEdges);
  }

  /**
   * @param v the vertex to get the adjacency list for
   * @return an iterable over the adjacency list of v
   */
  public Collection<Edge> adjEdges(int v) {
    return Collections.unmodifiableSet(adj.get(v));
  }

  @Override
  public int v() {
    return adj.size();
  }

  @Override
  public int e() {
    int e = 0;
    for ( Set<Edge> adjSet : adj ) {
      e += adjSet.size();
    }
    return e;
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder("EdgeWeightedDigraph()");
    for ( Edge edge : edges() ) {
      int v = edge.either();
      string.append("\n  .").append("edge(").append(v).append(", ").append(edge.other(v))
          .append(", ").append(edge.weight()).append(")");
    }
    return string.toString();
  }

  @Override
  public final int hashCode() {
    int result = HASH_SEED;
    for ( int v = 0; v < v(); v++ ) {
      result = result * HASH_SHIFT + adj(v).hashCode();
    }
    return result;
  }

  @Override
  public final boolean equals(Object other) {
    if ( this == other ) { return true; }
    if ( ! ( other instanceof EdgeWeightedDigraph ) ) { return false; }
    EdgeWeightedDigraph that = (EdgeWeightedDigraph)other;
    return ( this.v() == that.v() ) &&
           ( this.e() == that.e() ) &&
           ( this.edges().equals(that.edges()) );
  }

  /**
   * @return a digraph containing the same edges with negated weights
   */
  public EdgeWeightedDigraph negateEdges() {
    EdgeWeightedDigraph neg = new EdgeWeightedDigraph();
    for ( int v = 0; v < v(); v++ ) {
      for ( Edge e : adjEdges(v) ) {
        neg.edge(e.from(), e.to(), -e.weight());
      }
    }
    return neg;
  }
}
