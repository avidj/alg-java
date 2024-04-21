package org.avidd.unionfind;

/**
 * The quick union algorithm is an implementation of union find that builds
 * trees of ids allowing for a quick union (in constant time) but a slower find,
 * potentially logarithmic but the trees will often be unbalanced.
 */
public class QuickUnion implements UnionFind {
  private final int[] id;

  public QuickUnion(int n) {
    id = new int[n];
    for ( int i = 0; i < n; i++ ) {
      id[i] = i;
    }
  }

  @Override
  public void union(int p, int q) {
    int i = root(p); // find the root of p
    int j = root(q); // find the root of q
    id[i] = id[j]; // i is now a son of j (merge the trees)
  }

  @Override
  public boolean connected(int p, int q) {
    return root(p) == root(q); // connected if in the same tree (if they have
                               // the same roots)
  }

  private int root(int i) {
    while ( i != id[i] ) { // the root has itself as id
      i = id[i]; // follow the path to the root
    }
    return i; // the root
  }

  @Override
  public int find(int i) {
    return root(i);
  }
  
  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append("QU(");
    if ( id.length > 0 ) {
      string.append(0).append(":").append(root(0));
    }
    for ( int i = 1; i < id.length; i++ ) {
      string.append(", ").append(i).append(":").append(root(i));
    }
    return string.append(")").toString();
  }
}
