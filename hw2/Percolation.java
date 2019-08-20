package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int N;
    private WeightedQuickUnionUF aUnion;
    private WeightedQuickUnionUF aTopOnlyUnion;
    private int topSentinel;
    private int bottomSentinel;
    private int count;

    /**create N-by-N grid with all sites initially blocked*/
    public Percolation(int N){
        if(N <= 0){
            throw new IllegalArgumentException("N cannot be negative!");
        }
        this.N = N;
        count = 0;
        grid = new int[N][N];
        topSentinel = 0;
        bottomSentinel = N*N+1;
        aUnion = new WeightedQuickUnionUF(( N*N)+2);
        aTopOnlyUnion = new WeightedQuickUnionUF((N*N+1));

    }

    /**Maps 2D coordinates to an integer*/
    private int xyTo1D(int row, int col){
        return row*N + col + 1;
    }

    /**open the site (row, col) if it is not open already*/
    public void open(int row, int col){
        validate(row,col);
        if(!isOpen(row,col)){
            grid[row][col] = 1;
            count++;
        }

        if(row == 0){
            aUnion.union(topSentinel, xyTo1D(row,col));
            aTopOnlyUnion.union(topSentinel, xyTo1D(row,col));
        }else if(row == N-1){
            aUnion.union(bottomSentinel,xyTo1D(row,col));
        }
        if(row > 0 && isOpen(row-1,col)){
            aUnion.union(xyTo1D(row,col),xyTo1D(row-1,col));
            aTopOnlyUnion.union(xyTo1D(row,col),xyTo1D(row-1,col));
        }
        if(row < N-1 && isOpen(row+1,col)){
            aUnion.union(xyTo1D(row,col),xyTo1D(row+1,col));
            aTopOnlyUnion.union(xyTo1D(row,col),xyTo1D(row+1,col));
        }
        if(col > 0 && isOpen(row,col-1)){
            aUnion.union(xyTo1D(row,col),xyTo1D(row,col-1));
            aTopOnlyUnion.union(xyTo1D(row,col),xyTo1D(row,col-1));
        }
        if(col < N-1 && isOpen(row,col+1)){
            aUnion.union(xyTo1D(row,col),xyTo1D(row,col+1));
            aTopOnlyUnion.union(xyTo1D(row,col),xyTo1D(row,col+1));
        }

    }

    /**is the site (row, col) open?*/
    public boolean isOpen(int row, int col){
        validate(row,col);
        return grid[row][col] == 1;
    }

    /**is the site (row, col) full?*/
    public boolean isFull(int row, int col){
        return aTopOnlyUnion.connected(xyTo1D(row,col),topSentinel);
    }

    /**number of open sites*/
    public int numberOfOpenSites(){
        return count;
    }

    /**does the system percolates?*/
    public boolean percolates(){
        return aUnion.connected(topSentinel,bottomSentinel);
    }

    /**grid print*/
    public void print(){
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void validate(int row, int col){
        if(row < 0 || row >= N || col  < 0 || col >= N){
            throw new IndexOutOfBoundsException();
        }
    }

    public static void main(String[] args){



    }

}
