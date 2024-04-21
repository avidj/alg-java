package org.avidd.graph;

import org.avidd.graph.directed.Digraph;

/**
 * An undirected graph.
 * 
 * @author David Kensche
 */
public class Graph extends Digraph {

  @Override
  public Graph edge(int u, int v) {
    super.edge(u, v);
    super.edge(v, u);
    return this;
  }

  @Override
  public int e() {
    return super.e() / 2;
  }
}
