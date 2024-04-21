package org.avidd.graph;

class BlockingConnectedComponents implements ConnectedComponents {
  private int count = 0;
  private final int[] id;
  private final boolean[] marked;

  BlockingConnectedComponents(Graph g) {
    marked = new boolean[g.v()];
    id = new int[g.v()];
    computeComponents(g);
  }

  private void computeComponents(Graph g) {
    for ( int v = 0, n = g.v(); v < n; v++ ) {
      if ( !marked[v] ) {
        dfs(g, v);
        count++;
      }
    }
  }

  private void dfs(Graph g, int v) {
    marked[v] = true;
    id[v] = count;
    for ( int w : g.adj(v) ) {
      if ( !marked[w] ) {
        dfs(g, w);
      }
    }
  }

  @Override
  public boolean connected(int v, int w) {
    return id[v] == id[w];
  }

  @Override
  public int count() {
    return count;
  }

  @Override
  public int id(int v) {
    return id[v];
  }

  public String toString() {
    StringBuilder string = new StringBuilder("ConnectedComponents( id = { ");
    if ( id.length == 0 ) {
      return string.append("} )").toString();
    }
    string.append(0).append(" : ").append(id[0]);
    for ( int i = 1; i < id.length; i++ ) {
      string.append(", ").append(i).append(" : ").append(id[i]);
    }
    return string.append(" } )").toString();
  }
}
