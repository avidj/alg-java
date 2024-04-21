package org.avidd.graph.weighted;

/**
 * A flow edge is an edge in a flow graph, a graph that has a flow that can be maximized and a cut
 * that can be minimized. A flow edge further provides methods for conveniently querying the current
 * flow and the residual capacity, and for adding a certain flow to it.
 */
class FlowEdge {
  private static final int HASH_SEED = 997;
  private static final int RADIX = 31;
  private final int v;
  private final int w;
  private final double capacity;
  private final int hashCode;
  private double flow;

  FlowEdge(int aV, int aW, double aCapacity) {
    v = aV;
    w = aW;
    capacity = aCapacity;
    flow = 0.0;
    hashCode = computeHashCode();
  }

  public int from() {
    return v;
  }

  public int to() {
    return w;
  }

  public int other(int vertex) {
    if ( vertex == v ) {
      return w;
    } else if ( vertex == w ) {
      return v;
    }
    throw new IllegalArgumentException("No such vertex in this edge: " + this);
  }

  public double capacity() {
    return capacity;
  }

  public double flow() {
    return flow;
  }

  public double residualCapacityTo(int vertex) {
    if ( vertex == v ) {
      return capacity - flow;
    } else if ( vertex == w ) {
      return flow;
    }
    throw new IllegalArgumentException("No such vertex in this edge: " + this);
  }

  public void addResidualFlowTo(int vertex, double delta) {
    if ( vertex == v ) {
      if ( flow + delta > capacity ) {
        throw new RuntimeException();
      }
      flow += delta;
    } else if ( vertex == w ) {
      if ( flow - delta < 0 ) {
        throw new RuntimeException();
      }
      flow -= delta;
    } else {
      throw new IllegalArgumentException("No such vertex in this edge: " + this);
    }
  }

  @Override
  public String toString() {
    return new StringBuilder("FlowEdge(v = ").append(v).append(", w = ").append(w).append(", ")
        .append(flow).append(" / ").append(capacity).append(")").toString();
  }

  public static FlowEdge edge(int v, int w, double capacity) {
    return new FlowEdge(v, w, capacity);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  private int computeHashCode() {
    int result = HASH_SEED;
    result = result * RADIX + v;
    result = result * RADIX + w;
    return result;
  }

  @Override
  public boolean equals(Object other) {
    if ( ! ( other instanceof FlowEdge ) ) {
      return false;
    }
    FlowEdge that = (FlowEdge)other;
    return this.v == that.v && this.w == that.w;
  }
}
