import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private int[][] blocks;
    private int[][] intialBoardCopy;
    private int[][] correctBoard;
    private int[][] twinboard;

    public Board(int[][] blocks) {          // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.intialBoardCopy = new int[blocks.length][blocks.length];
        this.blocks = blocks;

        // Deep copy of 2 dem array.
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                this.intialBoardCopy[i][j] = blocks[i][j];
            }
        }

//        for (int e = 0; e < blocks.length; e++) {
//            for (int f = 0; f < blocks.length; f++) {
//                int correctValues = 1;
//                correctBoard[e][f] = correctValues;
//                correctValues++;
//            }
//        }
    }

    public int dimension() {                // board dimension n
        return intialBoardCopy.length;
    }

    public int hamming() {                  // number of blocks out of place
        int hammingCount = 0;
        int correctValue = 0;

        for (int i = 0; i < intialBoardCopy.length; i++) {
            for (int j = 0; j < intialBoardCopy.length; j++) {
                int rolColvalue = intialBoardCopy[i][j];

                correctValue++;
                if (rolColvalue == correctValue && rolColvalue == 0) {
                    continue;
                }
                hammingCount++;
            }
        }

        return hammingCount;
    }

    public int manhattan() {                // sum of Manhattan distances between blocks and goal
        int manhattanValue = 0;
        for (int i = 0; i < intialBoardCopy.length; i++) {
            for (int j = 0; j < intialBoardCopy.length; i++) {
                int goal = 1;
                int blockValue = intialBoardCopy[i][j];

                if (blockValue != goal) {
                    // Subtract indexes from the correct value and take the abs of that value.

                }
                goal++;
            }
        }
        return manhattanValue;
    }

    public boolean isGoal() {               // is this board the goal board?
        return hamming() == 0;
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
        } else {
            int swapValue = twinboard[1][0];
            twinboard[1][1] = twinboard[1][0];
            twinboard[1][1] = swapValue;

            return new Board(twinboard);
        }
    }


    public boolean equals(Object y) {       // does this board equal y?
        if (y == this) {
            return true;
        }
        if (!(y instanceof Board)) {
            return false;
        }
        Board otherBoard = (Board) y;
        if (!(Arrays.deepEquals(otherBoard.blocks, ((Board) y).blocks))) {
            return false;
        }
        return true;
    }

    public Iterable<Board> neighbors() {    // all neighboring boards

        ArrayList<Board> listOfBoards = new ArrayList<>();
        listOfBoards.add(new Board(intialBoardCopy));

//        if ()

        return null;
    }

    public String toString() {              // string representation of this board (in the output format specified below)
        int n = dimension();

        StringBuilder str = new StringBuilder();
        str.append(n + "\n");
        for (int i = 0; i < n; i++) {
            str.append(String.format("%2d ", intialBoardCopy));
        }
        str.append("\n");

        return str.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)

        // create initial board from file

        int[][] realBoardComplete = {

                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}};

        Board boardExample = new Board(realBoardComplete);


    }
}