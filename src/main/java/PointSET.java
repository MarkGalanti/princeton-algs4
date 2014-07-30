/**************************************************
 * Coursera - Algorithms, Part I Princetion University
 * https://class.coursera.org/algs4partI-005
 * 
 * Programming Assignment 5: Kd-Trees
 **************************************************/

import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> st;

    // construct an empty set of pointsk
    public PointSET() {
        st = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return st.isEmpty();
    }

    // number of points in the set
    public int size() {
        return st.size();
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        st.add(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return st.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        for (Point2D p : st) {
            p.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Set<Point2D> iter = new TreeSet<Point2D>();
        for (Point2D p : st) {
            if (rect.contains(p)) {
                iter.add(p);
            }
        }
        return iter;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (st.isEmpty()) {
            return null;
        }
        double minDist = Double.MAX_VALUE;
        Point2D nearestPoint = null;
        for (Point2D po : st) {
            if (nearestPoint == null) {
                nearestPoint = po;
                minDist = po.distanceTo(p);
            } else {
                double dist = po.distanceTo(p);
                if (dist < minDist) {
                    nearestPoint = po;
                    minDist = dist;
                }
            }
        }
        return nearestPoint;
    }
}
