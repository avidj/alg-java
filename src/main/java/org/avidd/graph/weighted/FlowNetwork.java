package org.avidd.graph.weighted;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.avidd.graph.GenericGraph;

/**
 * A flow network is a graph where each edge has a flow and a flow capacity. There exists a maximum
 * flow from a source to a sink but this must be computed by an appropriate algorithm.
 */
public class FlowNetwork implements GenericGraph {
  private static final int RADIX = 31;
  private static final int HASH_SEED = 997;
  private final List<Set<FlowEdge>> adj;

  /**
   * Create a new weighted graph represented as an edge list.
   */
  public FlowNetwork() {
    adj = new ArrayList<Set<FlowEdge>>();
  }

  /**
   * Create a new edge in this graph and return this graph for convenience.
   * 
   * @param v the origin of the edge
   * @param w the destination of the edge
   * @param weight the weight of the edge
   * @return this graph, for convenient concatenation of edges
   */
  public FlowNetwork edge(int v, int w, double capacity) {
    int m = Math.max(v, w);
    for ( int i = adj.size(); i <= m; i++ ) {
      adj.add(new HashSet<FlowEdge>());
    }
    // create the edge
    FlowEdge edge = FlowEdge.edge(v, w, capacity);
    adj.get(v).add(edge); // add forward edge
    adj.get(w).add(edge); // add backward edge
    return this;
  }

  /**
   * @return an iterable over the edges in this graph
   */
  public Set<FlowEdge> edges() {
    Set<FlowEdge> allEdges = new HashSet<FlowEdge>();
    for ( Set<FlowEdge> edges : adj ) {
      allEdges.addAll(edges);
    }
    return Collections.unmodifiableSet(allEdges);
  }

  @Override
  public Collection<Integer> adj(int v) {
    Set<Integer> vAdj = new HashSet<Integer>();
    for ( FlowEdge edge : adj.get(v) ) {
      vAdj.add(edge.to());
    }
    return Collections.unmodifiableSet(vAdj);
  }

  /**
   * @param v
   *          the vertex to get the adjacency list for
   * @return an iterable over the adjacency list of v
   */
  public Collection<FlowEdge> adjEdges(int v) {
    return Collections.unmodifiableSet(adj.get(v));
  }

  @Override
  public int v() {
    return adj.size();
  }

  @Override
  public int e() {
    int e = 0;
    for ( Set<FlowEdge> adjSet : adj ) {
      e += adjSet.size();
    }
    return e / 2;
  }

  @Override
  public boolean equals(Object other) {
    if ( ! ( other instanceof FlowNetwork ) ) {
      return false;
    }
    FlowNetwork that = (FlowNetwork)other;
    return this.adj.equals(that.adj);
  }

  @Override
  public int hashCode() {
    int result = HASH_SEED;
    result = result * RADIX + adj.hashCode();
    return result;
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder("FlowNetwork()");
    for ( FlowEdge edge : edges() ) {
      int v = edge.from();
      string.append("\n  .").append("edge(").append(v).append(", ").append(edge.other(v))
          .append(", ").append(edge.capacity()).append(")");
    }
    return string.toString();
  }
}