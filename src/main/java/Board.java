/**************************************************
 * Coursera - Algorithms, Part I Princetion University
 * https://class.coursera.org/algs4partI-005
 *
 * Programming Assignment 4: 8 Puzzle
 **************************************************/

import java.util.ArrayList;
import java.util.List;

public class Board {
    // blocks
    private int[][] blks;

    // manhattan distance, for cache
    private int manhDist;

    // hamming distance, for cache
    private int hammDist;

    // zero block x coordinate
    private int zx;

    // zero block y coordinate
    private int zy;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        int n = blocks.length;
        blks = new int[n][n];
        manhDist = 0;
        hammDist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blks[i][j] = blocks[i][j];
                if (blks[i][j] == 0) {
                    zx = i;
                    zy = j;
                } else {
                    int px = (blks[i][j] - 1) / n;
                    int py = (blks[i][j] - 1) % n;
                    manhDist += Math.abs(i - px) + Math.abs(j - py);

                    int pos = i * n + j;
                    if ((pos + 1) != blks[i][j]) {
                        hammDist++;
                    }
                }
            }
        }
    }

    private Board(Board parent, int manhDistChange) {
        int n = parent.blks.length;
        blks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blks[i][j] = parent.blks[i][j];
                if (blks[i][j] == 0) {
                    zx = i;
                    zy = j;
                } else {
                    int pos = i * n + j;
                    if ((pos + 1) != blks[i][j]) {
                        hammDist++;
                    }
                }
            }
        }
        manhDist = parent.manhattan() + manhDistChange;
    }

    // board dimension N
    public int dimension() {
        return blks.length;
    }

    // number of blocks out of place
    public int hamming() {
        return hammDist;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        return manhDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int n = dimension();
        int[][] newBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newBlocks[i][j] = blks[i][j];
            }
        }

        int t;
        if (newBlocks[0][0] == 0 || newBlocks[0][1] == 0) {
            t = newBlocks[1][0];
            newBlocks[1][0] = newBlocks[1][1];
            newBlocks[1][1] = t;
        } else {
            t = newBlocks[0][0];
            newBlocks[0][0] = newBlocks[0][1];
            newBlocks[0][1] = t;
        }

        return new Board(newBlocks);
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }
        Board that = (Board) y;
        if (zx != that.zx || zy != that.zy || manhDist != that.manhDist
                || hammDist != that.hammDist) {
            return false;
        }

        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blks[i][j] != that.blks[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neis = new ArrayList<Board>();

        int n = dimension();
        if (zx != 0) {
            int changed = 0;
            int pos = blks[zx - 1][zy] - 1;
            int x = pos / n;
            int y = pos % n;
            changed += -(Math.abs(zx - 1 - x) + Math.abs(zy - y));
            blks[zx][zy] = blks[zx - 1][zy];
            blks[zx - 1][zy] = 0;
            changed += Math.abs(zx - x) + Math.abs(zy - y);
            neis.add(new Board(this, changed));
            blks[zx - 1][zy] = blks[zx][zy];
            blks[zx][zy] = 0;
        }
        if (zx != n - 1) {
            int changed = 0;
            int pos = blks[zx + 1][zy] - 1;
            int x = pos / n;
            int y = pos % n;
            changed += -(Math.abs(zx + 1 - x) + Math.abs(zy - y));
            blks[zx][zy] = blks[zx + 1][zy];
            blks[zx + 1][zy] = 0;
            changed += Math.abs(zx - x) + Math.abs(zy - y);
            neis.add(new Board(this, changed));
            blks[zx + 1][zy] = blks[zx][zy];
            blks[zx][zy] = 0;
        }
        if (zy != 0) {
            int changed = 0;
            int pos = blks[zx][zy - 1] - 1;
            int x = pos / n;
            int y = pos % n;
            changed += -(Math.abs(zx - x) + Math.abs(zy - 1 - y));
            blks[zx][zy] = blks[zx][zy - 1];
            blks[zx][zy - 1] = 0;
            changed += Math.abs(zx - x) + Math.abs(zy - y);
            neis.add(new Board(this, changed));
            blks[zx][zy - 1] = blks[zx][zy];
            blks[zx][zy] = 0;
        }
        if (zy != n - 1) {
            int changed = 0;
            int pos = blks[zx][zy + 1] - 1;
            int x = pos / n;
            int y = pos % n;
            changed += -(Math.abs(zx - x) + Math.abs(zy + 1 - y));
            blks[zx][zy] = blks[zx][zy + 1];
            blks[zx][zy + 1] = 0;
            changed += Math.abs(zx - x) + Math.abs(zy - y);
            neis.add(new Board(this, changed));
            blks[zx][zy + 1] = blks[zx][zy];
            blks[zx][zy] = 0;
        }

        return neis;
    }

    // string representation of the board (in the output format specified
    // below)
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(dimension());
        sb.append("\n");

        int n = dimension();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", blks[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
