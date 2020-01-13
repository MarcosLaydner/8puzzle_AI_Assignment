

import java.util.ArrayList;
import java.util.Arrays;

public class GridState {

    private final int GRID_SIZE = 9;
    private int misplaced;
    private final int[] GOAL = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    private int[] curBoard;

    public GridState(int[] grid) {
        curBoard = grid;
        countMisplaced();
    }

    // this method generates the child nodes, that is, finds the available moves.
    // as the grid is being abstracted as an array with size 9, it is needed
    // to check the position on the array, to see what sides are possible to slide to.
    public ArrayList<GridState> generateChildren() {

        ArrayList<GridState> children = new ArrayList<>();
        int hole = getHole();

        // try to slide left, will not be possible if the tile
        // is on the left side column
        if (hole != 0 && hole != 3 && hole != 6) {
            swapAndStore(hole - 1, hole, children);
        }

        // try to slide up, will not be possible if the tile
        //is on the top row
        if (hole != 6 && hole != 7 && hole != 8) {
            swapAndStore(hole + 3, hole, children);
        }

        // try to slide down, will not be possible if the tile
        //s on the right side column
        if (hole != 0 && hole != 1 && hole != 2) {
            swapAndStore(hole - 3, hole, children);
        }

        // try to slide right, will not be possible if the tile
        //is on the bottom row
        if (hole != 2 && hole != 5 && hole != 8) {
            swapAndStore(hole + 1, hole, children);
        }

        return children;
    }

    // finds the greedy cost by calculation the distance from the tile to
    // its goal position. This is done for every tile and summed up.
    public double findGCost() {
        int gCost = 0;
        for (int i = 0; i < curBoard.length; i++) {
            int goalNumber = (GOAL[i] == 0) ? 9 : GOAL[i];
            gCost += Math.abs(curBoard[i] - goalNumber);
        }
        return gCost;
    }

    private void swapAndStore(int d1, int d2, ArrayList<GridState> state) {
        int[] copy = copyBoard(curBoard);
        int temp = copy[d1];
        copy[d1] = curBoard[d2];
        copy[d2] = temp;
        state.add((new GridState(copy)));
    }

    public boolean isGoal() {
        return Arrays.equals(curBoard, GOAL);
    }

    private int getHole() {
        // If returning -1 hole doesn't exist. This is not supposed to happen
        int holeIndex = -1;

        for (int i = 0; i < GRID_SIZE; i++) {
            if (curBoard[i] == 0)
                holeIndex = i;
        }
        return holeIndex;
    }

    private void countMisplaced() {
        for (int i = 0; i < curBoard.length; i++) {
            if (curBoard[i] != GOAL[i]) {
                misplaced++;
            }
        }
    }

    private int[] copyBoard(int[] state) {
        int[] ret = new int[GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            ret[i] = state[i];
        }
        return ret;
    }

    public int getMisplaced() {
        return misplaced;
    }

    public int[] getCurBoard() {
        return curBoard;
    }
}
