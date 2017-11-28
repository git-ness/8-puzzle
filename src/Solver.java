import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.MinPQ;

public class Solver {
    private Board initial;
    private ArrayList<Board> solutionBoardList;

    public Solver(Board initial) {            // find a solution to the initial board (using the A* algorithm)

        if (initial != null) {

            this.initial = initial;
            MinPQ<ArrayList<Board>> boardQueue = new MinPQ<>(new BoardComparitor());
            MinPQ<ArrayList<Board>> boardQueueTwin = new MinPQ<>(new BoardComparitor());

            ArrayList<Board> boardMovements = new ArrayList<>();
            ArrayList<Board> boardMovementsTwin = new ArrayList<>();

            boardMovements.add(initial);
            boardMovements.add(initial.twin());

            boardQueue.insert(boardMovements);
            boardQueueTwin.insert(boardMovementsTwin);

            while (true) {
                ArrayList<Board> canidateBoardSequence = boardQueue.delMin();
                Board canidateLastBoard = canidateBoardSequence.get(canidateBoardSequence.size()-1);

                if (canidateLastBoard.isGoal()) {
                    this.solutionBoardList = canidateBoardSequence;
                    return;
                }

                Board previousAttemptedBoard;
                if (canidateBoardSequence.size() < 2) {
                    previousAttemptedBoard = null;
                }
                else {
                    canidateBoardSequence.get(canidateBoardSequence.size() - 2);

                    for (Board next : canidateLastBoard.neighbors()) {
                        if (null != previousAttemptedBoard && previousAttemptedBoard.equals(next)) {
                            continue;
                        }

                        ArrayList<Board> nextBoardList = new ArrayList<>();
                        nextBoardList.addAll(canidateBoardSequence);
                        nextBoardList.add(next);
                        boardQueue.insert(nextBoardList);
                    }
                }

                ArrayList<Board> canidateBoardSequenceTwin = boardQueueTwin.delMin();
                Board canidatelastBoardTwin = canidateBoardSequenceTwin.get(canidateBoardSequenceTwin.size() - 1);

                if (canidateBoardSequenceTwin.get(canidateBoardSequenceTwin.size() - 1).isGoal()) {
                    solutionBoardList = null;
                    return;
                }

                Board previouslyAttemptedBoard;
                if (canidateBoardSequenceTwin.size() < 2) {
                    previousAttemptedBoard = null;
                } else {

                    for (Board next : canidatelastBoardTwin.neighbors()) {
                        previouslyAttemptedBoard = canidateBoardSequenceTwin.get(canidateBoardSequenceTwin.size() - 2);
                        if (previouslyAttemptedBoard != null && previouslyAttemptedBoard.equals(next)) {
                            continue;
                        }

                        ArrayList<Board> nextBoardList = new ArrayList<>();
                        nextBoardList.addAll(canidateBoardSequenceTwin);
                        nextBoardList.add(next);
                        boardQueueTwin.insert(nextBoardList);
                    }
                }
            }
        }

        else {
            throw new IllegalArgumentException();
        }
    }

    private class BoardComparitor implements Comparator<ArrayList<Board>> {

        @Override
        public int compare(ArrayList<Board> o1, ArrayList<Board> o2) {
            Board board1 = o1.get(o1.size() - 1);
            Board board2 = o2.get(o2.size() - 1);

            int manhattanDistance1 = board1.manhattan() + o1.size() - 1;
            int manhattanDistance2 = board2.manhattan() + o2.size() - 1;

            if (manhattanDistance1 < manhattanDistance2) {
                return -1;
            }

            if (manhattanDistance1 == manhattanDistance2) {
                return 0;
            }

            return 1;
        }
    }

    public boolean isSolvable() {             // is the initial board solvable?
        return false;
    }

    public int moves() {                      // min number of moves to solve initial board; -1 if unsolvable
        return 0;
    }

    public Iterable<Board> solution() {        // sequence of boards in a shortest solution; null if unsolvable
        return null;
    }

    public static void main(String[] args) {  // solve a slider puzzle (given below)

    }
}