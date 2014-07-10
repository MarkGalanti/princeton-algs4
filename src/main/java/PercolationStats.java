/**************************************************
 * Cousera - Algorithms, Part I Princetion University link:
 * https://class.coursera.org/algs4partI-005
 * 
 * Programming Assignment 1: Percolation
 **************************************************/

public class PercolationStats {
    private double[] ths;

    // private int n;

    private int t;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        // n = N;
        t = T;

        ths = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation perco = new Percolation(N);
            int openBlocks = 0;
            while (!perco.percolates()) {
                int x = StdRandom.uniform(1, N + 1);
                int y = StdRandom.uniform(1, N + 1);
                if (!perco.isOpen(x, y)) {
                    perco.open(x, y);
                    openBlocks++;
                }
            }
            ths[i] = (double) openBlocks / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(ths);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(ths);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt((double) t));
    }

    // returns lower upper of the 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt((double) t));
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException();
        }

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", "
                + ps.confidenceHi());
    }
}
