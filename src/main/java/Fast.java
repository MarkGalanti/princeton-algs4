import java.util.Arrays;

public class Fast {
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
        Point[] pointsCopy = points.clone();

        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : pointsCopy) {
            p.draw();
            Arrays.sort(points);
            Arrays.sort(points, p.SLOPE_ORDER);

            int i = 0;
            while (i < points.length) {
                if (p.compareTo(points[i]) >= 0) {
                    i++;
                    continue;
                }

                int start = i, end = i;
                double v = p.slopeTo(points[i]);
                while ((++i) < points.length && p.slopeTo(points[i]) == v) {
                    end++;
                }
                if (end - start >= 2
                        && (start == 0 || p.slopeTo(points[start - 1]) != p
                                .slopeTo(points[start]))) {
                    String line = p.toString() + " -> ";
                    for (int j = start; j < end; j++) {
                        line += points[j].toString() + " -> ";
                    }
                    line += points[end].toString();

                    StdOut.println(line);
                    p.drawTo(points[end]);
                }
            }
        }
    }
}
