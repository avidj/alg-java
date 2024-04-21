package org.avidd.unionfind;

/**
 * The weighted quick union is guaranteed to produce (almost) balanced trees by
 * always glueing the smaller tree under the root of the larger tree in union
 * operations. This guarantees logarithmic find-performance (and logarithmic
 * union performance).
 */
public class WeightedQuickUnion implements UnionFind {
  final int[] id;
  final int[] size;

  public WeightedQuickUnion(int n) {
    id = new int[n];
    size = new int[n]; // the size of each set is initially 1
    for ( int i = 0; i < n; i++ ) {
      id[i] = i;
      size[i] = 1;
    }
  }

  @Override
  public void union(int p, int q) {
    int i = root(p); // find the root of p
    int j = root(q); // and the root of q
    if ( i != j ) { // if they are not already in the same set (tree)
      if ( size[i] < size[j] ) { // if root(p)'s size is smaller than root(q)'s
        id[i] = j; // then root(p) is now a son of root(q) = j
        size[j] += size[i]; // the size of root(q) is now incremented by the
                            // size of root(p)
      } else { // or vice versa
        id[j] = i;
        size[i] += size[j];
      }
    }
  }

  @Override
  public boolean connected(int p, int q) {
    return root(p) == root(q); // connected if they have the same root
  }

  @Override
  public int find(int i) {
    return root(i);
  }

  int root(int i) {
    while ( i != id[i] ) {
      i = id[i];
    }
    return i;
  }
  
  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append("WQU(");
    if ( id.length > 0 ) {
      string.append(0).append(":").append(root(0));
    }
    for ( int i = 1; i < id.length; i++ ) {
      string.append(", ").append(i).append(":").append(root(i));
    }
    return string.append(")").toString();
  }
}
