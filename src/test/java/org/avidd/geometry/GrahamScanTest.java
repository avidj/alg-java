package org.avidd.geometry;

import static org.avidd.geometry.Point2D.point;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class GrahamScanTest {

  @Test
  public void testGrahamScan() {
    List<Point2D> points = Arrays.asList(point(1.0, 7.0), point(5.0, 3.0), point(7.0, 8.0),
        point(6.0, 1.0), point(9.0, 9.0), point(4.0, 6.0), point(2.0, 0.0), point(8.0, 5.0),
        point(3.0, 4.0), point(0.0, 2.0));

    List<Point2D> convexHull = GrahamScan.convexHull(points);

    System.err.println(convexHull);
  }

}
