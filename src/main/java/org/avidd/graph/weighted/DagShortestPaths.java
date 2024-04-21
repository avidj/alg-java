package org.avidd.graph.weighted;

import java.util.LinkedList;
import java.util.List;

import org.avidd.graph.ShortestPaths;
import org.avidd.graph.directed.BlockingTopologicalSort;
import org.avidd.graph.directed.TopologicalSort;

/**
 * In a directed acyclic graph, to compute the shortest paths it is sufficient to relax edges in the
 * order of a simple topological sort in O(V + E). 
 * 
 * This algorithm is preferred for graphs without directed cycles.
 */
public class DagShortestPaths implements ShortestPaths {
  private Edge[] edgeTo;
  private double[] distTo;

  public DagShortestPaths(EdgeWeightedDigraph g, int s) {
    edgeTo = new Edge[g.v()];
    distTo = new double[g.v()];
    for ( int v = 0; v < g.v(); v++ ) {
      distTo[v] = Double.POSITIVE_INFINITY;
    }
    distTo[s] = 0;
    TopologicalSort top = new BlockingTopologicalSort(g);
    for ( int v : top.getTopologicalOrder() ) {
      for ( Edge e : g.adjEdges(v) ) {
        relax(e);
      }
    }
  }

  private void relax(Edge e) {
    int v = e.from(), w = e.to();
    if ( distTo[w] > distTo[v] + e.weight() ) {
      distTo[w] = distTo[v] + e.weight();
      edgeTo[w] = e;
    }
  }

  @Override
  public double distTo(int v) {
    return distTo[v];
  }

  @Override
  public List<Edge> pathTo(int v) {
    List<Edge> path = new LinkedList<Edge>();
    for ( Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()] ) {
      path.add(0, e);
    }
    return path;
  }
}
