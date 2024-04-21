package org.avidd.graph.weighted;

/**
 * A weighted directed graph edge.
 * 
 * @author David Kensche
 */
public final class Edge implements Comparable<Edge> {
  private static final int HASH_SEED = 997;
  private static final int HASH_SHIFT = 31;
  private final int v;
  private final int w;
  private final double weight;
  private final int hashCode;

  /**
   * Static factory method for edges.
   * 
   * @param v
   *          the origin of the edge
   * @param w
   *          the destination of the edge
   * @param weight
   *          the weight of the edge
   * @return the new edge
   */
  static Edge edge(int v, int w, double weight) {
    return new Edge(v, w, weight);
  }

  private Edge(int aV, int aW, double aWeight) {
    v = aV;
    w = aW;
    weight = aWeight;
    hashCode = computeHashCode();
  }

  @Override
  public int compareTo(Edge other) {
    return (int)Math.signum(weight - other.weight);
  }

  public int from() {
    return v;
  }

  public int to() {
    return w;
  }

  /**
   * @return any of the nodes in the edge, in fact, the origin
   */
  public int either() {
    return v;
  }

  /**
   * Given any of the nodes in the edge, returns the other node.
   * 
   * @param v
   *          any of the nodes in the edge
   * @return the other node in this edge
   */
  public int other(int v) {
    if ( this.v == v ) {
      return w;
    }
    return this.v;
  }

  /**
   * @return the weight of the edge
   */
  public double weight() {
    return weight;
  }

  /**
   * {@inheritDoc} Edges are considered equal if they have the same origin and
   * destination. Thus, the weight is ignored.
   */
  @Override
  public boolean equals(Object other) {
    if ( ! ( other instanceof Edge ) ) {
      return false;
    }
    Edge that = (Edge)other;
    return ( v == that.either() && w == that.other(v) )
        || ( w == that.either() && v == that.other(w) );
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  private int computeHashCode() {
    int result = HASH_SEED;
    result = result * HASH_SHIFT + v;
    result = result * HASH_SHIFT + w;
    return result;
  }

  @Override
  public String toString() {
    return new StringBuilder("(").append(v).append(",").append(w).append(",w=").append(weight)
        .append(")").toString();
  }
}
