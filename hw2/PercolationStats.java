package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] fractions;
    private int numTrials;


    /**perform T independent experiments on an N-by-N grid*/
    public PercolationStats(int N, int T, PercolationFactory pf){
        if(N<=0 || T <= 0){
            throw new IllegalArgumentException("N or T can't be negative!");
        }
       numTrials = T;
        fractions = new double[T];
        Percolation p = pf.make(N);
        for(int i=0; i<T; i++){
            int openSites = 0;
            while(!p.percolates()){
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if(!p.isOpen(row,col)){
                    p.open(row,col);
                    openSites += 1;
                }
            }
            fractions[i] = (double) openSites / (N*N);
        }


    }

    /**sample mean of percolation threshold*/
    public double mean(){
        return StdStats.mean(fractions);
    }

    /**ample standard deviation of percolation threshold*/
    public double stddev(){
        return StdStats.stddev(fractions);
    }

    /**low endpoint of 95% confidence interval*/
    public double confidenceLow(){
        double mu = mean();
        double sigma = stddev();
        return mu - 1.96 * sigma / Math.sqrt(numTrials);
    }

    /**high endpoint of 95% confidence interval*/
    public double confidenceHigh(){
        double mu = mean();
        double sigma = stddev();
        return mu + 1.96 * sigma / Math.sqrt(numTrials);
    }

}
