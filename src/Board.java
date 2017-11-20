import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Board {

    private int[][] blocks;
    private int[][] intialBoardCopy;
    private int[][] twinboard;

    public Board(int[][] blocks) {          // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)

        this.blocks = blocks;

        // Deep copy of 2 dem array.
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                this.intialBoardCopy[i][j] = blocks[i][j];
            }
        }
    }

    public int dimension() {                // board dimension n
        return intialBoardCopy.length;
    }

    public int hamming() {                  // number of blocks out of place
        int hammingSum = 0;

        for (int i = 0; i < intialBoardCopy.length; i++) {
            for (int j = 0; j < intialBoardCopy.length; j++) {

                if (intialBoardCopy[i][j] != hammingSum) {
                    hammingSum++;
                }
            }
        }

        return hammingSum;
    }

    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        return 0;
    }

    public boolean isGoal() {               // is this board the goal board?
        return false;
    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks

        // Deep copy that will soon return the twin board.
        for (int i = 0; i < intialBoardCopy.length; i++) {
            for (int j = 0; j < intialBoardCopy.length; j++) {
                this.twinboard[i][j] = intialBoardCopy[i][j];
            }
        }

        if (twinboard[0][0] != 0 || twinboard[0][1] != 0) {
            int swapValue = twinboard[0][0];
            twinboard[0][1] = twinboard[0][0];
            twinboard[0][1] = swapValue;

            return new Board(twinboard);
        }

        else {
            int swapValue = twinboard[1][0];
            twinboard[1][1] = twinboard[1][0];
            twinboard[1][1] = swapValue;

            return new Board(twinboard);
        }
    }


    public boolean equals(Object y) {       // does this board equal y?
        if (y == this) {return true;}
        if (!(y instanceof Board)) { return false;}
        Board otherBoard = (Board) y;
        if(!(Arrays.deepEquals(otherBoard.blocks, ((Board) y).blocks))) {return false;}
        return true;
    }

    public Iterable<Board> neighbors() {    // all neighboring boards
        return null;
    }

    public String toString() {              // string representation of this board (in the output format specified below)
        return null;
    }

    public static void main(String[] args) { // unit tests (not graded)

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}



