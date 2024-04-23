package org.avidd.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.avidd.graph.weighted.Edge;
import org.avidd.graph.weighted.EdgeWeightedDigraph;

/**
 * The Bellman-Ford algorithm iterates V times over E edges (naively) and relaxes them to compute
 * the shortest paths in a cyclic directed graph without negative cycles. As a little optimization
 * this implementation uses a queue of nodes whose distance changed during the previous pass as 
 * other nodes need not be considered.
 *  
 * This algorithm can be used for graphs with negative weights but without negative cycles. 
 */
public class BellmanFord implements ShortestPaths {
  private final double[] distTo;
  private final Edge[] edgeTo;

  public BellmanFord(EdgeWeightedDigraph g, int s) {
    distTo = new double[g.v()];
    edgeTo = new Edge[g.v()];
    Arrays.fill(distTo, Double.POSITIVE_INFINITY);
    List<Integer> relaxedPrev; // must copy to avoid concurrent modification
    Set<Integer> relaxed = new LinkedHashSet<>();
    distTo[s] = 0;
    relaxed.add(0);
    for ( int i = 0; i < g.v(); i++ ) {
      relaxedPrev = new ArrayList<>(relaxed);
      relaxed.clear();
      for ( int v : relaxedPrev )
        for ( Edge e : g.adjEdges(v) )
          relax(e, relaxed);
    }
  }

  private void relax(Edge e, Set<Integer> relaxed) {
    int v = e.from();
    int w = e.to();
    if ( distTo[w] > distTo[v] + e.weight() ) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
      relaxed.add(w);
    }
  }

  @Override
  public double distTo(int v) {
    return distTo[v];
  }

  @Override
  public List<Edge> pathTo(int v) {
    List<Edge> path = new LinkedList<>();
    for ( Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()] ) {
      path.add(0, e);
    }
    return Collections.unmodifiableList(path);
  }

}
