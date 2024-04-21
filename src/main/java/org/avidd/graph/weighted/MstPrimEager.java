package org.avidd.graph.weighted;

import org.avidd.queues.IndexedMinHeap;
import org.avidd.queues.IndexedPriorityQueue;
import org.avidd.util.ComparableComparator;

/**
 * Prim's algorithm is a greedy variant of the MST computation. This is the
 * eager Prim variant. 
 * * consider edges in ascending order of weight using an indexed heap for up-to-date distances
 * * add edges to the MST if they don't produce a cycle 
 * * cycles are detected by marking nodes which are already part of the ST
 */
public final class MstPrimEager implements MstStrategy {

  @Override
  public EdgeWeightedGraph mst(EdgeWeightedGraph graph) {
    final boolean[] marked = new boolean[graph.v()];
    final EdgeWeightedGraph mst = new EdgeWeightedGraph();
    final IndexedPriorityQueue<Edge> pq = new IndexedMinHeap<Edge>(
        new ComparableComparator<Edge>(), graph.e());

    visit(graph, 0, marked, pq); // we assume the graph is connected
    while ( !pq.isEmpty() && mst.e() < graph.v() - 1 ) {
      Edge edge = pq.pop().getValue(); // pop min weight edge
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

  private void visit(EdgeWeightedGraph graph, int v, boolean[] marked, IndexedPriorityQueue<Edge> pq) {
    marked[v] = true;
    for ( Edge edge : graph.adjEdges(v) ) {
      int w = edge.other(v);
      if ( marked[w] ) {
        continue;
      }
      if ( !pq.contains(w) ) {
        pq.insert(w, edge);
      } else if ( pq.peek(w).getValue().weight() > edge.weight() ) {
        pq.update(w, edge);
      }
    }
  }
}
