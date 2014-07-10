/**************************************************
 * Coursera - Algorithms, Part I Princetion University
 * https://class.coursera.org/algs4partI-005
 * 
 * Programming Assignment 1: Percolation
 **************************************************/

public class Percolation {
    private byte[][] sites; // 0 for blocked and 1 for open

    private WeightedQuickUnionUF qu;

    private WeightedQuickUnionUF qu2; // for prevent backwash

    private int size;

    private int top;

    private int bottom;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        size = N;
        sites = new byte[N][N];

        qu = new WeightedQuickUnionUF(N * N + 2);
        qu2 = new WeightedQuickUnionUF(N * N + 1);
        top = N * N;
        bottom = N * N + 1;
    }

    // convert row and column to index
    private int rcToIndex(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IndexOutOfBoundsException();
        }

        return (i - 1) * size + (j - 1);
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IndexOutOfBoundsException();
        }

        sites[i - 1][j - 1] = 1;

        if (i == 1) {
            qu.union(top, rcToIndex(i, j));
            qu2.union(top, rcToIndex(i, j));
        }
        if (i == size) {
            qu.union(bottom, rcToIndex(i, j));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            qu.union(rcToIndex(i, j), rcToIndex(i - 1, j));
            qu2.union(rcToIndex(i, j), rcToIndex(i - 1, j));
        }
        if (i < size && isOpen(i + 1, j)) {
            qu.union(rcToIndex(i, j), rcToIndex(i + 1, j));
            qu2.union(rcToIndex(i, j), rcToIndex(i + 1, j));
        }
        if (j > 1 && isOpen(i, j - 1)) {
            qu.union(rcToIndex(i, j), rcToIndex(i, j - 1));
            qu2.union(rcToIndex(i, j), rcToIndex(i, j - 1));
        }
        if (j < size && isOpen(i, j + 1)) {
            qu.union(rcToIndex(i, j), rcToIndex(i, j + 1));
            qu2.union(rcToIndex(i, j), rcToIndex(i, j + 1));
        }
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i < 1 || i > size || j < 1 || j > size) {
            throw new IndexOutOfBoundsException();
        }

        return sites[i - 1][j - 1] == 1;
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        return isOpen(i, j) && qu2.connected(top, rcToIndex(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return qu.connected(top, bottom);
    }
}
