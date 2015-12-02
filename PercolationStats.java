import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int T;
    private double mean;
    private double stddev;


    public PercolationStats(int N, int T) {
        Percolation percolation;
        double[] tresholds;
        if (N <= 0 || T <= 0) throw new IllegalArgumentException();
        this.T = T;
        tresholds = new double[T]; // array for our unbelievable results
        int[] a = new int[N*N + 1]; // array for generating random sites
        int opened = 0;
        for (int m = 0; m <= N*N; m++) // filling our beautiful array
            a[m] = m;
        for (int k = 0; k < T; k++) { // providing T amazing experiments
            percolation = new Percolation(N);
            StdRandom.shuffle(a, 1, N*N);
            while (!percolation.percolates()) {
                opened++;
                if(a[opened] % N != 0) percolation.open(a[opened]/N + 1, a[opened] % N);
                else percolation.open(a[opened]/N, N);
            }
            tresholds[k] = opened;
            mean = StdStats.mean(tresholds, 0, k)/(N*N);
            stddev = StdStats.stddev(tresholds, 0, k)/(N*N);
            opened = 0;
        }
    }

    public double mean() {
        return mean;
    }

    public double stddev() { return stddev; }

    public double confidenceLo() { return (mean() - 1.96*stddev()/Math.sqrt(T)); }

    public double confidenceHi() {
        return (mean() + 1.96*stddev()/Math.sqrt(T));
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats stats = new PercolationStats(N, T);
        String mean = "mean";
        String stddev = "stddev";
        String confidenceInterval = "95% confidence interval";
        StdOut.printf("%-20.12s = %-40.17f\n", mean, stats.mean());
        StdOut.printf("%-20.12s = %-40.17f\n", stddev, stats.stddev());
        StdOut.printf("%-20.12s = %-17.17f, %-40.17f", confidenceInterval, stats.confidenceLo(), stats.confidenceHi());
    }
}

