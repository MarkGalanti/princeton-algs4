/**************************************************
 * Coursera - Algorithms, Part I Princetion University
 * https://class.coursera.org/algs4partI-005
 * 
 * Programming Assignment 5: Kd-Trees
 **************************************************/

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static class Node {
        private Point2D p;
        
        private RectHV rect;
        
        private Node lb;
        
        private Node rt;
    }
    
    private int sz = 0;

    // is the set empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // number of points in the set
    public int size() {
        return sz;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (st == null) {
            st = new KdNode(p, true);
        }
        st.insert(p);
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (st == null) {
            return false;
        }

        return st.contains(p);
    }

    // draw all of the points to standard draw
    public void draw() {
        if (!isEmpty()) {
            st.draw();
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> iter = new ArrayList<Point2D>();
        if (st != null) {
            st.range(rect, iter);
        }
        return iter;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (isEmpty()) {
            return null;
        }
        Point2D nearestPoint = st.point;

        return nearestPoint;
    }
}
