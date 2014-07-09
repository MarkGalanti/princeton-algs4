import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        In in = new In(args[0]);

        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            p.draw();
            for (int j = i + 1; j < points.length; j++) {
                Point q = points[j];
                double stq = p.slopeTo(q);
                for (int k = j + 1; k < points.length; k++) {
                    Point r = points[k];
                    double str = p.slopeTo(r);
                    if (stq != str) {
                        continue;
                    }
                    for (int t = k + 1; t < points.length; t++) {
                        Point s = points[t];
                        double sts = p.slopeTo(s);
                        if (stq != sts) {
                            continue;
                        }

                        StdOut.println(p.toString() + " -> " + q.toString()
                                + " -> " + r.toString() + " -> " + s.toString());

                        p.drawTo(s);
                    }
                }
            }
        }
    }
}
