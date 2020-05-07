
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double[] testCollection;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        testCollection = new double[trials];
        runExperiments(n, trials);
    }

    private void runExperiments(int n, int trials) {
        for (int i = 0; i < trials; i++) {
            testCollection[i] = runExperiment(n);
        } 	
    }

    private double runExperiment(int n) {
        Percolation test = new Percolation(n);
        int openNode = 0;
        do {
            int row = randomInt(n);
            int col = randomInt(n);
            if (!test.isOpen(row, col)) {
                test.open(row, col);
                openNode++;
            }   		
        } while (!test.percolates());
        return (double) openNode / ((double) n * n);
    }


    private int randomInt(int n) {
        return StdRandom.uniform(1, n+1);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(testCollection);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(testCollection);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence();
    }

    // high endpoint of 95% confidence interval
    private double confidence() {
        return 1.96 * stddev() / Math.sqrt(testCollection.length);
    }

    public double confidenceHi() {
        return mean() + confidence();
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch time = new Stopwatch();
        int n;
        int trials;   
        if (args.length > 0) {
            try {
                n = Integer.parseInt(args[0]);
                trials = Integer.parseInt(args[1]);
                PercolationStats abc = new PercolationStats(n, trials);

                System.out.println("mean                    = " + abc.mean());
                System.out.println("stddev                  = " + abc.stddev());
                System.out.println("95% confidence interval = [" + abc.confidenceLo() + ", " + abc.confidenceHi() + "]");

            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.err.println("Argument" + args[1] + " must be an integer.");
            }
        }
        System.out.println(time.elapsedTime());

    }
}
