package org.avidd.unionfind;

/**
 * QuickFind is an implementation of union find that quickly finds the set for
 * any object. This is achieved by maintaining an array of set-ids that is
 * always being kept up to date.
 * 
 * The union operation has linear time costs in the size of the set. This is
 * very poor.
 */
public class QuickFind implements UnionFind {
  private final int[] id;

  public QuickFind(int n) {
    // initialize the id-set with distinct ids
    id = new int[n];
    for ( int i = 0; i < n; i++ ) {
      id[i] = i;
    }
  }

  @Override
  public void union(int p, int q) {
    if ( connected(p, q) ) {
      return;
    } // done already
    int pid = id[p];
    int qid = id[q];

    for ( int i = 0; i < id.length; i++ ) { // traverse through all n (!)
                                            // elements and set id to qid
      if ( id[i] == pid ) {
        id[i] = qid;
      }
    }
  }

  @Override
  public boolean connected(int p, int q) {
    return id[p] == id[q]; // connected if they have the same id
  }

  @Override
  public int find(int i) {
    return id[i]; // return just the id of i which is always up-to-date
  }

  @Override
  public String toString() {
    return new StringBuilder("QuickFind( id = { ").append(toString(id)).append(" } )").toString();
  }

  public static String toString(int[] ints) {
    StringBuilder string = new StringBuilder();
    if ( ints.length > 0 ) {
      string.append(ints[0]);
    }
    for ( int i = 1; i < ints.length; i++ ) {
      string.append(" ");
      string.append(ints[i]);
    }
    return string.toString();
  }
}
