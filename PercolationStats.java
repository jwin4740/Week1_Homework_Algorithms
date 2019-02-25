import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int n;
    private final int trials;
    private final double[] trialRes;
    private final double conf = 1.96;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("N and Trials must be greater than 0");
        }

        this.n = n;
        this.trials = trials;
        trialRes = new double[trials];
        for (int i = 0; i < trials; i++) {
            trialRes[i] = runPercolationTrial();
        }
    }

    public double mean() {


        // System.out.println("Mean: " + StdStats.mean(trialRes));

        return StdStats.mean(trialRes);
    }

    private double runPercolationTrial() {
        Percolation percolation = new Percolation(n);
        boolean percolated = false;
        while (!percolated) {
            int i = StdRandom.uniform(n) + 1;
            int j = StdRandom.uniform(n) + 1;
            percolation.open(i, j);
            percolated = percolation.percolates();
        }
        double numSites = percolation.numberOfOpenSites();
        double perc = numSites / (n * n);
        return perc;

    }

    public double stddev() {
        // System.out.println("standardDeviation: " + StdStats.stddev(trialRes));
        return StdStats.stddev(trialRes);
    }                 // sample standard deviation of percolation threshold

    public double confidenceLo() {
        return StdStats.mean(trialRes) - (conf * StdStats.stddev(trialRes)) / Math.sqrt(trials);

    }                // low  endpoint of 95% confidence interval

    public double confidenceHi() {
        return StdStats.mean(trialRes) + (conf * StdStats.stddev(trialRes)) / Math.sqrt(trials);
    }                // high endpoint of 95% confidence interval

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, t);
        percolationStats.mean();
        percolationStats.stddev();
        percolationStats.confidenceLo();
        percolationStats.confidenceHi();

    }
}
