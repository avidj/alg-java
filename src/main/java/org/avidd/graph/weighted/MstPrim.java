package org.avidd.graph.weighted;

import org.avidd.queues.MinHeap;
import org.avidd.util.ComparableComparator;

/**
 * Prim's algorithm is a greedy variant of the MST computation. 
 * * consider edges in ascending order of distance from the already existing ST 
 * * add edges to the MST if they don't produce a cycle 
 * * to detect cycles, visited nodes are marked
 */
public final class MstPrim implements MstStrategy {

  @Override
  public EdgeWeightedGraph mst(EdgeWeightedGraph graph) {
    final boolean[] marked = new boolean[graph.v()];
    final EdgeWeightedGraph mst = new EdgeWeightedGraph();
    final MinHeap<Edge> pq = new MinHeap<Edge>(new ComparableComparator<Edge>(), graph.e());

    visit(graph, 0, marked, pq); // we assume the graph is connected
    while ( !pq.isEmpty() && mst.e() < graph.v() - 1 ) {
      Edge edge = pq.pop(); // pop min weight edge
      int v = edge.either();
      int w = edge.other(v);
      if ( marked[v] && marked[w] ) {
        continue;
      } // ignore if both vertices are in mst
      if ( !marked[v] ) {
        visit(graph, v, marked, pq);
      } // add v or w to tree
      if ( !marked[w] ) {
        visit(graph, w, marked, pq);
      }
      mst.edge(v, w, edge.weight()); // add edge
    }
    return mst;
  }

  private void visit(EdgeWeightedGraph graph, int v, boolean[] marked, MinHeap<Edge> minHeap) {
    marked[v] = true;
    for ( Edge edge : graph.adjEdges(v) ) {
      if ( !marked[edge.other(v)] ) {
        minHeap.insert(edge);
      }
    }
  }
}
