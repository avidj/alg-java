package org.avidd.geometry;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public final class GrahamScan {

  public static List<Point2D> convexHull(List<Point2D> aPoints) {
    Stack<Point2D> hull = new Stack<Point2D>();
    // we start at the point with smallest y
    Collections.sort(aPoints, Point2D.yOrder());

    // now the first element is the one with smallest y
    // we consider points in polar order relative to the start
    Collections.sort(aPoints, aPoints.get(0).polarOrder());
    System.err.println("polar order from point " + aPoints.get(0) + ": " + aPoints);

    hull.push(aPoints.get(0));
    hull.push(aPoints.get(1));

    for ( int i = 2, n = aPoints.size(); i < n; i++ ) {
      Point2D top = hull.pop();
      System.err.println("top = " + top);
      while ( PointUtil.ccw(hull.peek(), top, aPoints.get(i)) <= 0 ) {
        System.err.println("discard " + top);
        top = hull.pop();
      }
      hull.push(top);
      hull.push(aPoints.get(i));
    }
    return hull;
  }
}
