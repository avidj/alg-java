package org.avidd.unionfind;

/**
 * Given a set of N objects union find determines for any pair of objects
 * whether they are connected over a path. To do so, union find creates distinct
 * sets of size 1 for all objects and unions sets where possible. If in the end
 * two objects are in the same set, then they are reachable from each other.
 * Algorithms for union-find have very different performance characteristics.
 */
public interface UnionFind {

  /**
   * Unions the two sets p and q belong to.
   * 
   * @param p
   *          object p
   * @param q
   *          object q
   */
  public void union(int p, int q);

  /**
   * @param p
   *          object p
   * @param q
   *          object q
   * @return true, iff p and q are connected
   */
  public boolean connected(int p, int q);

  /**
   * @param i
   *          the object to find
   * @return the set that i belongs to
   */
  public int find(int i);

  // public int count();

}
