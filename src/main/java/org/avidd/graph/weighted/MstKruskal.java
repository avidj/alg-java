package org.avidd.graph.weighted;

import org.avidd.queues.MinHeap;
import org.avidd.unionfind.UnionFind;
import org.avidd.unionfind.WeightedQuickUnionPathCompression;
import org.avidd.util.ComparableComparator;

/**
 * Kruskal's algorithm is a greedy variant of the MST computation. 
 * * consider edges in ascending order of weight 
 * * add edges to the MST if they don't produce a cycle 
 * * producing a cycle is tested in practically constant time using union find
 */
public final class MstKruskal implements MstStrategy {

  @Override
  public EdgeWeightedGraph mst(EdgeWeightedGraph graph) {
    EdgeWeightedGraph mst = new EdgeWeightedGraph();
    // maintain edges in a min heap
    final MinHeap<Edge> minHeap = 
        new MinHeap<>(new ComparableComparator<Edge>(), graph.edges());
    // maintain union-find to detect cycles
    UnionFind uf = new WeightedQuickUnionPathCompression(graph.v());
    while ( !minHeap.isEmpty() && mst.e() < graph.v() - 1 ) {
      Edge edge = minHeap.pop();
      int v = edge.either();
      int w = edge.other(v);
      if ( !uf.connected(v, w) ) { // use union find to detect cycles
        uf.union(v, w);
        mst.edge(v, w, edge.weight());
      }
    }
    return mst;
  }

}
