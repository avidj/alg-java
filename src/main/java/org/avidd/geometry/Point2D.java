package org.avidd.geometry;

import java.util.Comparator;

public final class Point2D implements Comparable<Point2D> {
  private final int hashCode;

  final double x;
  final double y;

  private Point2D(double aX, double aY) {
    x = aX;
    y = aY;
    hashCode = computeHashCode();
  }

  private int computeHashCode() {
    int result = 17;
    result = result * 31 + Double.valueOf(x).hashCode();
    result = result * 31 + Double.valueOf(y).hashCode();
    return result;
  }

  public static Point2D point(double aX, double aY) {
    return new Point2D(aX, aY);
  }

  public Comparator<Point2D> polarOrder() {
    return POLAR_ORDER;
  }

  public static Comparator<Point2D> yOrder() {
    return Y_ORDER;
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  @Override
  public boolean equals(Object that) {
    if ( ! ( that instanceof Point2D ) )
      return false;
    Point2D point = (Point2D)that;
    return ( this.x == point.x && this.y == point.y );
  }

  @Override
  public int compareTo(Point2D o) {
    return polarOrder().compare(this, o);
  }

  @Override
  public String toString() {
    return new StringBuilder("Point2D( x = ").append(x).append(", y = ").append(y).append(" )")
        .toString();
  }

  private final Comparator<Point2D> POLAR_ORDER = new Comparator<Point2D>() {
    @Override
    public int compare(Point2D q1, Point2D q2) {
      double dy1 = q1.y - y;
      double dy2 = q2.y - y;

      if ( dy1 == 0 && dy2 == 0 ) {
        if ( q1.x < q2.x )
          return -1;
        else if ( q1.x > q2.x )
          return +1;
        else
          return 0;
      } // p, q1, q2 are horizontal
      else if ( dy1 >= 0 && dy2 < 0 ) {
        return -1;
      } // q1 is above p, q2 is below p
      else if ( dy2 >= 0 && dy1 < 0 ) {
        return +1;
      } // q2 is above p, q1 is below p
      else
        return -PointUtil.ccw(Point2D.this, q1, q2);
    }
  };

  private static final Comparator<Point2D> Y_ORDER = new Comparator<>() {
    @Override
    public int compare(Point2D q1, Point2D q2) {
      return Double.valueOf(q1.y).compareTo(q2.y);
    }
  };
}
