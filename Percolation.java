import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private boolean[][] grid;
    private final int leng;
    private final WeightedQuickUnionUF wquf;
    private final int virtualTop;
    private final int virtualBottom;


    public Percolation(int n) {
        inputValidation(n);
        leng = n;
        grid = new boolean[n + 2][n + 1];
        initGrid();
        // renderGrid();

        virtualTop = leng - (leng / 2);
        virtualBottom = (leng + 2) * (leng + 1) - (leng / 2);
        wquf = new WeightedQuickUnionUF((n + 2) * (n + 1));


    }

    private void initGrid() {
        for (int i = 0; i <= leng + 1; i++) {
            for (int j = 0; j <= leng; j++) {
                grid[i][j] = false;

            }
        }

    }

    // private void renderGrid(){
    //     for (int i = 0; i <= leng + 1; i++) {
    //         for (int j = 0; j <= leng; j++) {
    //             if (i == 0 || j == 0 || i == leng + 1) {
    //                 System.out.print("  BBBBB  ");
    //             }
    //
    //             else {
    //                 System.out.print("  " + grid[i][j] + "  ");
    //             }
    //
    //         }
    //         System.out.println("\n");
    //     }
    //     System.out.println("\n\n\n");
    //
    // }

    private void inputValidation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be greater than 0");
        }

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {


        if (!isOpen(row, col)) {
            grid[row][col] = true;
            int sing = convertToSingleIndex(row, col);
            //        System.out.println("COORDINATE: " + row + "," + col + "    SINGLE_INDEX: " + sing);

            // connect anything in row 1 to virtualtop
            // if (sing >= leng + 2 && sing <= leng *2 + 1) {
            //     wquf.union(virtualTop, sing);
            // }
            if (row == 1) {
                wquf.union(virtualTop, sing);

            }
            if (row == leng) {
                wquf.union(virtualBottom, sing);

            }
            makeAdjacentUnions(row, col);
        }

        // System.out.println("COORDINATE: " + row + "," + col + "   SINGLE_INDEX: " + sing);
        // renderGrid();
        //        if (numberOfOpenSites() > 80) {
        ////            System.out.println("progress");
        //        }

    }


    private void makeAdjacentUnions(int row, int col) {

        int baseSingle = convertToSingleIndex(row, col);

        // top
        if (row != 1 && isOpen(row - 1, col)) {
            wquf.union(baseSingle, baseSingle - (leng + 1));
        }

        // bottom
        if (row != leng && isOpen(row + 1, col)) {
            wquf.union(baseSingle, baseSingle + (leng + 1));
        }

        // left
        if (col != 1 && isOpen(row, col - 1)) {
            wquf.union(baseSingle, baseSingle - 1);
        }

        // right
        if (col != leng && isOpen(row, col + 1)) {
            wquf.union(baseSingle, baseSingle + 1);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > leng || col <= 0 || col > leng) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        return grid[row][col];
    }


    public boolean isFull(int row, int col) {
        if (row <= 0 || row > leng || col <= 0 || col > leng) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        int sing = convertToSingleIndex(row, col);
        if (wquf.connected(virtualTop, sing) && isOpen(row, col)) {
            return true;
        }
        return false;

    }

    private int convertToSingleIndex(int row, int col) {
        return (leng + 1) * row + col;
    }

    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i <= leng + 1; i++) {
            for (int j = 0; j <= leng; j++) {
                if (grid[i][j]) count++;

            }
        }
        return count;

    }

    public boolean percolates() {
        return wquf.connected(virtualTop, virtualBottom);
    }


    public static void main(String[] args) {

        // test client (described below)

    }
}