import java.util.ArrayList;
import java.util.Arrays;

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
        this.indexOfCorrectBoard = new ArrayList<>();
        for (int e = 0; e < blocks.length; e++) {
            for (int f = 0; f < blocks.length; f++) {
                correctBoard[e][f] = correctValues;
                indexOfCorrectValues(correctValues, e, f);

                correctValues++;
            }
        }
        // Sets the last value to be the "blank" space, where 0 is used.
        correctBoard[blocks.length - 1][blocks.length - 1] = 0;
    }

    private void indexOfCorrectValues(int correctValues, int e, int f) {
        IndexOfValues indexOfValues = new IndexOfValues(correctValues, e, f);
        this.indexOfCorrectBoard.add(indexOfValues);
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

        int iCorrectIndex = 0;
        int jCorrectIndex = 0;
        int iDifference;
        int jDifference;

        IndexOfValues index = indexOfCorrectBoard.get(4);
        int iIndex = index.getiIndex();
        int jIndex = index.getjIndex();

        System.out.println("iIndex = " + iIndex + " jIndex = " + jIndex);


        for (int i = 0; i < initialBoardCopy.length; i++) {
            for (int j = 0; j < initialBoardCopy.length; j++) {

                // Grab the i and j location of the correct value to
                // find the absolute difference for the Manhattan distance.

                if (iCorrectIndex == initialBoardCopy.length) {
                    iCorrectIndex = 0;
                }

                if (jCorrectIndex == initialBoardCopy.length) {
                    jCorrectIndex = 0;
                }

                int blockValue = initialBoardCopy[i][j];

                if (blockValue != goal && initialBoardCopy[i][j] != 0) {
                    // To find the Manhattan distance, subtract indexes
                    // from the correct i and j location values and take the abs of that value
                    iDifference = Math.abs(iCorrectIndex);
                    jDifference = Math.abs(jCorrectIndex);

                    manhattanValue += iDifference;
                    manhattanValue += jDifference;
                }

                goal++;
                iCorrectIndex++;

            }
            jCorrectIndex++;
        }
        return 0;
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

        public int getIndexOfValue(int actualValue) {
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

        ArrayList<Board> listOfBoards = new ArrayList<>();
        listOfBoards.add(new Board(initialBoardCopy));

//        if ()

        return null;
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

                {1, 2, 7},
                {4, 0, 6},
                {3, 8, 5}}; // If it's 0 or doesn't match the goal, add the index to the arrayList.

        Board boardExample = new Board(realBoardComplete);
        System.out.println(boardExample.manhattan());


    }
}