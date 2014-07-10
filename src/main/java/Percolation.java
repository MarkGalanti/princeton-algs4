/**************************************************
 * Cousera - Algorithms, Part I Princetion University link:
 * https://class.coursera.org/algs4partI-005
 * 
 * Programming Assignment 1: Percolation
 **************************************************/

public class Percolation {
    private int[][] sites; // 0 for blocked and 1 for open

    private QuickUnionUF qu;

    private int size;

    private int top;

    private int bottom;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        size = N;
        sites = new int[N][N];

        qu = new QuickUnionUF(N * N + 2);
        top = N * N;
        bottom = N * N + 1;
        for (int i = 0; i < N; i++) {
            qu.union(top, i);
            qu.union(bottom, N * (N - 1) + i);
        }
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

        if (i > 1 && isOpen(i - 1, j)) {
            qu.union(rcToIndex(i, j), rcToIndex(i - 1, j));
        }
        if (i < size && isOpen(i + 1, j)) {
            qu.union(rcToIndex(i, j), rcToIndex(i + 1, j));
        }
        if (j > 1 && isOpen(i, j - 1)) {
            qu.union(rcToIndex(i, j), rcToIndex(i, j - 1));
        }
        if (j < size && isOpen(i, j + 1)) {
            qu.union(rcToIndex(i, j), rcToIndex(i, j + 1));
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
        return qu.connected(top, rcToIndex(i, j));
    }

    // does the system percolate?
    public boolean percolates() {
        return qu.connected(top, bottom);
    }
}
