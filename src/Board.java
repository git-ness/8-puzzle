import java.util.*;

public class Board {

    private int[][] blocks;
    private int[][] initialBoardCopy;
    private int[][] correctBoard;
    private ArrayList<IndexOfValues> indexOfCorrectBoard;
    private int[][] twinboard;

    public Board(int[][] blocks) {          // construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        this.initialBoardCopy = new int[blocks.length][blocks.length];
        this.correctBoard = new int[blocks.length][blocks.length];
        this.blocks = blocks;

        // Deep copy of 2 dem array.
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                this.initialBoardCopy[i][j] = blocks[i][j];
            }
        }

        int correctValues = 1;
        this.indexOfCorrectBoard = new ArrayList<>(1);
        if (this.indexOfCorrectBoard.size() == 0) {
            this.indexOfCorrectBoard.add(new IndexOfValues(-1, 0, 0));
        }
        for (int e = 0; e < blocks.length; e++) {
            for (int f = 0; f < blocks.length; f++) {
                correctBoard[e][f] = correctValues;
                if (correctValues != blocks.length * blocks.length) {
                    indexOfCorrectValues(correctValues, e, f);
                    correctValues++;
                } else {
                    indexOfCorrectValues(0, e, f);
                }

            }
        }
        // Sets the last value to be the "blank" space, where 0 is used.
        correctBoard[blocks.length - 1][blocks.length - 1] = 0;
    }

    private void indexOfCorrectValues(int correctValues, int e, int f) {
        IndexOfValues indexOfValues = new IndexOfValues(correctValues, e, f);
        this.indexOfCorrectBoard.add(indexOfCorrectBoard.size(), indexOfValues);
    }

    public int dimension() {                // board dimension n
        return initialBoardCopy.length;
    }

    public int hamming() {                  // number of blocks out of place
        int hammingCount = 0;
        int correctValue = 0;

        for (int i = 0; i < initialBoardCopy.length; i++) {
            for (int j = 0; j < initialBoardCopy.length; j++) {
                int rolColvalue = initialBoardCopy[i][j];

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
        int goal = 1;

        int iDifference;
        int jDifference;

        for (int i = 0; i < initialBoardCopy.length; i++) {
            for (int j = 0; j < initialBoardCopy.length; j++) {

                // Grab the i and j location of the correct value to
                // find the absolute difference for the Manhattan distance.

                int blockValue = initialBoardCopy[i][j];

                if (blockValue != goal && initialBoardCopy[i][j] != 0) {
                    // To find the Manhattan distance, subtract indexes
                    // from the correct i and j location values and take the abs of that value
                    iDifference = Math.abs(indexOfCorrectBoard.get(blockValue).getiIndex() - i);
                    jDifference = Math.abs(indexOfCorrectBoard.get(blockValue).getjIndex() - j);

                    manhattanValue += iDifference;
                    manhattanValue += jDifference;
                }
                goal++;
            }
        }
        return manhattanValue;
    }

    private class IndexOfValues {
        private int actualValue;
        private int iIndex;
        private int jIndex;

        public IndexOfValues(int actualValue, int iIndex, int jIndex) {
            this.actualValue = actualValue;
            this.iIndex = iIndex;
            this.jIndex = jIndex;
        }

        public int getIndexOfValue() {
            return actualValue;
        }

        public int getiIndex() {
            return iIndex;
        }

        public int getjIndex() {
            return jIndex;
        }


    }

    public boolean isGoal() {               // is this board the goal board?
        return hamming() == 0;
    }

    public Board twin() {                   // a board that is obtained by exchanging any pair of blocks

        // Deep copy that will soon return the twin board.
        for (int i = 0; i < initialBoardCopy.length; i++) {
            for (int j = 0; j < initialBoardCopy.length; j++) {
                this.twinboard[i][j] = initialBoardCopy[i][j];
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
        int blankCol = -1;
        int blankRow = -1;

        ArrayList<Board> neighborListOfBoards = new ArrayList<>();
        int[][] neighborCopy = new int[initialBoardCopy.length][initialBoardCopy.length];

        // Move blank space left
        if (blankCol != 0) {

            int blankRowPosition = -1;
            int blankColPosition = -1;

            for (int i = 0; i < neighborCopy.length; i++) {
                for (int j = 0; j < neighborCopy.length; j++) {
                    if (neighborCopy[i][j] == 0) {
                        blankRowPosition = i;
                        blankColPosition = j;

                        neighborCopy[i][j] = initialBoardCopy[i][j];
                    }
                }
            }

            int tempValue = neighborCopy[blankRowPosition][blankColPosition - 1];
            neighborCopy[blankRowPosition][blankColPosition] = tempValue;
            neighborCopy[blankRowPosition][blankColPosition - 1] = 0;

            neighborListOfBoards.add(new Board(neighborCopy));

        }

        // Move blank space right
        if (blankCol != neighborCopy.length - 1) {
            int blankRowPosition = -1;
            int blankColPosition = -1;

            for (int i = 0; i < neighborCopy.length; i++) {
                for (int j = 0; j < neighborCopy.length; j++) {
                    if (neighborCopy[i][j] == 0) {
                        blankRowPosition = i;
                        blankColPosition = j;

                        neighborCopy[i][j] = initialBoardCopy[i][j];
                    }
                }
            }
            int tempValue = neighborCopy[blankRowPosition][blankColPosition + 1];
            neighborCopy[blankRowPosition][blankColPosition] = tempValue;
            neighborCopy[blankRowPosition][blankColPosition + 1] = 0;

            neighborListOfBoards.add(new Board(neighborCopy));
        }

        // Move blank space up
        if (blankRow != 0) {
            int blankRowPosition = -1;
            int blankColPosition = -1;

            for (int i = 0; i < neighborCopy.length; i++) {
                for (int j = 0; j < neighborCopy.length; j++) {
                    if (neighborCopy[i][j] == 0) {
                        blankRowPosition = i;
                        blankColPosition = j;

                        neighborCopy[i][j] = initialBoardCopy[i][j];

                    }
                }
            }

            int tempValue = neighborCopy[blankRowPosition - 1][blankColPosition];
            neighborCopy[blankRowPosition][blankColPosition] = tempValue;
            neighborCopy[blankColPosition - 1][blankColPosition] = 0;

            neighborListOfBoards.add(new Board(neighborCopy));
        }

        // Move blank space down
        if (blankRow != neighborCopy.length - 1) {
            int blankRowPosition = -1;
            int blankColPosition = -1;

            for (int i = 0; i < neighborCopy.length; i++) {
                for (int j = 0; j < neighborCopy.length; j++) {
                    blankRowPosition = i;
                    blankColPosition = j;

                    neighborCopy[i][j] = initialBoardCopy[i][j];
                }
            }

            int tempValue = neighborCopy[blankRowPosition + 1][blankColPosition];
            neighborCopy[blankRowPosition][blankColPosition] = tempValue;
            neighborCopy[blankRowPosition][blankColPosition] = 0;

            neighborListOfBoards.add(new Board(neighborCopy));
        }
        return neighborListOfBoards;
    }

    public String toString() {
        int n = initialBoardCopy.length;

        StringBuilder s = new StringBuilder();
//        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", correctBoard[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) { // unit tests (not graded)

        // create initial board from file

        int[][] realBoardComplete = {

                {6, 0, 5},  // 3+2
                {7, 2, 8},  // 1+1+2
                {4, 1, 3}}; // 1+3+2


        Board boardExample = new Board(realBoardComplete);
        System.out.println(boardExample.manhattan());


    }
}