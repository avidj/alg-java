package org.avidd.unionfind;

/**
 * This is the optimal solution for union find. It is basically the same as the
 * weighted quick union except that it compresses paths during the root(i)
 * lookup method. As the paths have no meaning this is absolutely ok and it
 * tremendously speeds up future lookup operations leading to cost of union and
 * find of only lg*(n) which is (in this universe) <= 5 and hence constant and a
 * cost of union find of (N + M lg*N) which is linear!
 */
public class WeightedQuickUnionPathCompression extends WeightedQuickUnion {

  public WeightedQuickUnionPathCompression(int n) {
    super(n);
  }

  @Override
  public int find(int i) {
    return root(i);
  }

  int root(int i) {
    while ( i != id[i] ) {
      id[i] = id[id[i]];
      i = id[i];
    }
    return i;
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    string.append("WQUPC(");
    if ( id.length > 0 ) {
      string.append(0).append(":").append(root(0));
    }
    for ( int i = 1; i < id.length; i++ ) {
      string.append(", ").append(i).append(":").append(root(i));
    }
    return string.append(")").toString();
  }

}
