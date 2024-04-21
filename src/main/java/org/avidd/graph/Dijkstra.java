package org.avidd.graph;

import java.util.LinkedList;
import java.util.List;

import org.avidd.graph.weighted.Edge;
import org.avidd.graph.weighted.EdgeWeightedDigraph;
import org.avidd.queues.IndexedMinHeap;
import org.avidd.queues.IndexedPriorityQueue;
import org.avidd.util.ComparableComparator;

/**
 * Dijkstra's algorithm for single source shortest paths. The algorithm is based on edge relaxation
 * and considers edges in the order of increasing weight for nodes adjacent to newly relaxed edges,
 * thus, in order of increasing distance from the start.
 * For looking up the next candidate for relaxation this implementation uses a binary indexed heap.
 * 
 * This algorithm is preferred for graphs without negative weights.
 */
public class Dijkstra implements ShortestPaths {
  private final double[] distTo;
  private final Edge[] edgeTo;

  public Dijkstra(EdgeWeightedDigraph g, int s) {
    distTo = new double[g.v()];
    for ( int i = 0; i < g.v(); i++ ) {
      distTo[i] = Double.POSITIVE_INFINITY;
    }
    edgeTo = new Edge[g.v()];
    dijkstra(g, s);
  }

  private void dijkstra(EdgeWeightedDigraph g, int s) {
    IndexedPriorityQueue<Double> pq = new IndexedMinHeap<>(
        new ComparableComparator<Double>(), g.v());
    distTo[s] = 0.0;
    pq.insert(s, 0.0);
    while ( !pq.isEmpty() ) {
      int v = pq.pop().getKey();
      for ( Edge e : g.adjEdges(v) ) {
        relax(e, pq);
      }
    }
  }

  private void relax(Edge e, IndexedPriorityQueue<Double> pq) {
    int v = e.from();
    int w = e.to();
    if ( distTo[w] > distTo[v] + e.weight() ) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
      if ( pq.contains(w) ) {
        pq.update(w, distTo[w]);
      } else {
        pq.insert(w, distTo[w]);
      }
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
    return path;
  }
}
