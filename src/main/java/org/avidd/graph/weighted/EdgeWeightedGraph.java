package org.avidd.graph.weighted;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.avidd.graph.GenericGraph;

/**
 * An edge weighted graph.
 */
public class EdgeWeightedGraph implements GenericGraph {
  private static final int HASH_SHIFT = 31;
  private static final int HASH_SEED = 997;
  private final List<List<Edge>> adj;

  /**
   * Create a new weighted graph represented as an edge list.
   */
  public EdgeWeightedGraph() {
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
  public EdgeWeightedGraph edge(int v, int w, double weight) {
    int m = Math.max(v, w);
    for ( int i = adj.size(); i <= m; i++ ) {
      adj.add(Collections.<Edge> emptyList());
    }
    // create the edge
    Edge edge = Edge.edge(v, w, weight);
    // add edge to both adjacency lists
    addEdge(v, edge);
    addEdge(w, edge);
    return this;
  }

  private void addEdge(int v, Edge e) {
    List<Edge> vAdj = adj.get(v);
    if ( vAdj.isEmpty() ) {
      vAdj = new LinkedList<>();
      adj.set(v, vAdj);
    }
    vAdj.add(e);
  }

  /**
   * @return an iterable over the edges in this graph
   */
  public Set<Edge> edges() {
    Set<Edge> allEdges = new HashSet<>();
    for ( List<Edge> edges : adj ) {
      allEdges.addAll(edges);
    }
    return Collections.unmodifiableSet(allEdges);
  }

  /**
   * @param v the vertex to get the adjacency list for
   * @return an iterable over the adjacency list of v
   */
  public List<Edge> adjEdges(int v) {
    return Collections.unmodifiableList(adj.get(v));
  }

  @Override
  public int v() {
    return adj.size();
  }

  @Override
  public int e() {
    int e = 0;
    for ( List<Edge> adjSet : adj ) {
      e += adjSet.size();
    }
    return e / 2;
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder("EdgeWeightedGraph()");
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
      result = result * HASH_SHIFT + adjEdges(v).hashCode();
    }
    return result;
  }

  @Override
  public final  boolean equals(Object other) {
    if ( this == other ) { return true; }
    if ( ! ( other instanceof EdgeWeightedGraph ) ) { return false; }
    EdgeWeightedGraph that = (EdgeWeightedGraph)other;
    return ( this.v() == that.v() ) &&
           ( this.e() == that.e() ) &&
           ( this.edges().equals(that.edges()) );
  }

  @Override
  public Iterable<Integer> adj(int v) {
    // TODO provide a proper iterator
    List<Integer> adj = new ArrayList<>();
    for ( Edge edge : adjEdges(v) ) {
      adj.add(edge.other(v));
    }
    return adj;
  }
}
