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

    private Node tree;

    private int sz;

    public KdTree() {
        tree = null;
        sz = 0;
    }

    private boolean isEven(int n) {
        return n % 2 == 0;
    }

    private boolean innerContains(Point2D p, Node node, int depth) {
        int de = depth;
        if (p.equals(node.p)) {
            return true;
        }
        if (isEven(de) && p.x() <= node.p.x() || !isEven(de)
                && p.y() <= node.p.y()) {
            if (node.lb == null) {
                return false;
            } else {
                return innerContains(p, node.lb, ++de);
            }
        } else if (isEven(de) && p.x() > node.p.x() || !isEven(de)
                && p.y() > node.p.y()) {
            if (node.rt == null) {
                return false;
            } else {
                return innerContains(p, node.rt, ++de);
            }
        }

        return false;
    }

    private void innerInsert(Point2D p, Node node, int depth) {
        int de = depth;
        if (isEven(de) && p.x() <= node.p.x() || !isEven(de)
                && p.y() <= node.p.y()) {
            if (node.lb == null) {
                RectHV newRect = null;
                if (isEven(de)) {
                    newRect = new RectHV(node.rect.xmin(), node.rect.ymin(),
                            node.p.x(), node.rect.ymax());
                } else {
                    newRect = new RectHV(node.rect.xmin(), node.rect.ymin(),
                            node.rect.xmax(), node.p.y());
                }
                Node newNode = new Node();
                newNode.p = p;
                newNode.lb = null;
                newNode.rt = null;
                newNode.rect = newRect;
                node.lb = newNode;
                sz++;
            } else {
                innerInsert(p, node.lb, ++de);
            }
        } else if (isEven(de) && p.x() > node.p.x() || !isEven(de)
                && p.y() > node.p.y()) {
            if (node.rt == null) {
                RectHV newRect = null;
                if (isEven(de)) {
                    newRect = new RectHV(node.p.x(), node.rect.ymin(),
                            node.rect.xmax(), node.rect.ymax());
                } else {
                    newRect = new RectHV(node.rect.xmin(), node.p.y(),
                            node.rect.xmax(), node.rect.ymax());
                }
                Node newNode = new Node();
                newNode.p = p;
                newNode.lb = null;
                newNode.rt = null;
                newNode.rect = newRect;
                node.rt = newNode;
                sz++;
            } else {
                innerInsert(p, node.rt, ++de);
            }
        }
    }

    private void innerDraw(Node node) {
        node.p.draw();
        node.rect.draw();
        if (node.lb != null) {
            innerDraw(node.lb);
        }
        if (node.rt != null) {
            innerDraw(node.rt);
        }
    }

    private void innerRange(Node node, RectHV rect, List<Point2D> a) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.p)) {
            a.add(node.p);
        }
        if (node.rect.intersects(rect)) {
            innerRange(node.lb, rect, a);
            innerRange(node.rt, rect, a);
        }
    }

    private Point2D innerNearest(Node node, Point2D p, Point2D nep) {
        Point2D np = nep;
        if (node == null) {
            return np;
        }
        if ((p.distanceSquaredTo(np)) < node.rect.distanceSquaredTo(p)) {
            return np;
        }
        if (p.distanceSquaredTo(node.p) < p.distanceSquaredTo(np)) {
            np = node.p;
        }
        if (node.lb == null && node.rt == null) {
            return np;
        }
        if (node.lb == null) {
            return innerNearest(node.rt, p, np);
        }
        if (node.rt == null) {
            return innerNearest(node.lb, p, np);
        }
        if (node.lb.rect.distanceSquaredTo(p) < node.rt.rect
                .distanceSquaredTo(p)) {
            np = innerNearest(node.lb, p, np);
            np = innerNearest(node.rt, p, np);
        } else {
            np = innerNearest(node.rt, p, np);
            np = innerNearest(node.lb, p, np);
        }
        return np;
    }

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
        if (contains(p)) {
            return;
        }
        if (sz == 0) {
            RectHV rect = new RectHV(0D, 0D, 1D, 1D);
            tree = new Node();
            tree.p = p;
            tree.lb = null;
            tree.rt = null;
            tree.rect = rect;
            sz++;
        } else {
            innerInsert(p, tree, 0);
        }
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        if (sz == 0) {
            return false;
        }
        return innerContains(p, tree, 0);
    }

    // draw all of the points to standard draw
    public void draw() {
        if (sz == 0) {
            return;
        }
        innerDraw(tree);
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> a = new ArrayList<Point2D>();
        innerRange(tree, rect, a);
        return a;
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        if (sz == 0) {
            return null;
        }
        return innerNearest(tree, p, tree.p);
    }
}
