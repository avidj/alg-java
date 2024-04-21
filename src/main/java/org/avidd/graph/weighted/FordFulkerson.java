package org.avidd.graph.weighted;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the classic algorithm for computing MAXFLOW and the dual
 * problem MINCUT. This implementation uses BFS to find augmenting paths. 
 * 
 * TODO: Using more sophisticated priority queues will improve the order of growth for
 * this algorithm's TIME performance.
 */
public class FordFulkerson {
  private boolean[] marked;
  private FlowEdge[] edgeTo;
  private double value;

  public FordFulkerson(FlowNetwork g, int s, int t) {
    value = 0.0;
    while ( hasAugmentingPath(g, s, t) ) {
      // determine bottleneck capacity (on the newly computed path)
      double bottle = Double.POSITIVE_INFINITY;
      for ( int v = t; v != s; v = edgeTo[v].other(v) )
        bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
      // add the bottleneck capacity to all edges on the _path_
      for ( int v = t; v != s; v = edgeTo[v].other(v) )
        edgeTo[v].addResidualFlowTo(v, bottle);
      // increment the value of the flow
      value += bottle;
    }
  }

  // compute an augmenting path as a side effect of determining whether one
  // exists
  private boolean hasAugmentingPath(FlowNetwork g, int s, int t) {
    edgeTo = new FlowEdge[g.v()];
    marked = new boolean[g.v()];
    // just do a BFS from s to t
    List<Integer> queue = new ArrayList<Integer>();
    queue.add(s);
    marked[s] = true;
    while ( !queue.isEmpty() ) {
      int v = queue.remove(0);
      for ( FlowEdge e : g.adjEdges(v) ) {
        int w = e.other(v);
        // if the edge has a residual capacity and the destination was not yet
        // visited
        if ( e.residualCapacityTo(w) > 0 && !marked[w] ) {
          edgeTo[w] = e; // last edge on path to w
          marked[w] = true; // mark w as part of the path
          queue.add(w); // enqueue w
        }
      }
    }
    return marked[t]; // Is the sink reachable from source in residual network?
  }

  /**
   * @return the value of the computed maximal flow
   */
  public double value() {
    return value;
  }

  /**
   * @param v the vertex to query
   * @return true iff v is reachable from the source in the residual network
   */
  public boolean inCut(int v) {
    return marked[v];
  }

}
