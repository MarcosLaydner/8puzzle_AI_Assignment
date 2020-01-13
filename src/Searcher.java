
import java.util.*;

// The search algorithm chosen was the A* Search, which will
// calculate the best next move according to number of misplaced tiles,
// and sum of the distances from the current tile to the correct one.
public class Searcher {

    private static int moves = 0;
    private static int expanded = 0;
    private static int generated = 0;

    public static void search(int[] initialGrid) {

        Node root = new Node(new GridState(initialGrid));
        generated++;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        if (isSolvable(initialGrid)) {

            while (!queue.isEmpty()) {

                Node curNode = queue.poll();

                //checks if the current node has the goal state
                if (!curNode.getCurState().isGoal()) {

                    ArrayList<GridState> tempChildren = curNode.getCurState().generateChildren();
                    expanded++;
                    generated += tempChildren.size();
                    ArrayList<Node> children = new ArrayList<>();

                    // checks for repeated nodes
                    for (int i = 0; i < tempChildren.size(); i++) {

                        Node checkedNode;

                        checkedNode = new Node(curNode, tempChildren.get(i), curNode.getGCost() +
                                tempChildren.get(i).findGCost(), tempChildren.get(i).getMisplaced());

                        if (!checkRepeats(checkedNode))
                            children.add(checkedNode);

                    }

                    // if the nodes has no children, goes to the next iteration of the loop,
                    // which means that the next node in the queue will be analyzed
                    if (children.size() == 0)
                        continue;

                    Node lowestNode = children.get(0);

                    //checks to see which of the children has the lowest overall cost
                    for (int i = 0; i < children.size(); i++) {
                        if (lowestNode.getCost() > children.get(i)
                                .getCost()) {
                            lowestNode = children.get(i);
                        }
                    }

                    int lowestValue = (int) lowestNode.getCost();

                    // adds any nodes that have that same value as the lowest
                    for (int i = 0; i < children.size(); i++) {

                        if (children.get(i).getCost() == lowestValue)
                            queue.add(children.get(i));

                    }

                } else {

                    // now that the goal configuration was found
                    // this creates a list of the moves done to be sent to the writer
                    // along with the amount of moves, expanded and unexpanded nodes
                    ArrayList<int[]> solutionPath = new ArrayList<>();

                    solutionPath.add(curNode.getCurState().getCurBoard());
                    curNode = curNode.getParent();

                    while (curNode.getParent() != null) {
                        solutionPath.add(curNode.getCurState().getCurBoard());
                        curNode = curNode.getParent();
                    }
                    solutionPath.add(curNode.getCurState().getCurBoard());

                    moves = solutionPath.size();

                    new SolutionWriter(moves, expanded, (generated - expanded), solutionPath);
                    return;
                }

            }
        } else {
            new SolutionWriter();
            return;
        }


    }


    //checks if the initial board is solvable.
    //to discover if a board is solvable, it is needed to
    // count the amount of occurrences of smaller numbers coming after later numbers
    // if this amount is even, the grid configuration is solvable.
    private static boolean isSolvable(int[] grid) {
        int count = 0;

        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = i + 1; j < grid.length; j++) {
                if (grid[i] != 0 && grid[j] != 0 && grid[i] > grid[j]) {
                    count++;
                }
            }
        }

        return count % 2 == 0;
    }

    // goes up in the tree to see if the state has been reached already
    // this is to prevent extra unnecessary moves
    private static boolean checkRepeats(Node n) {

        Node checkNode = n;

        while (n.getParent() != null) {
            if (Arrays.equals(n.getParent().getCurState().getCurBoard(), checkNode.getCurState().getCurBoard())) {
                return true;
            }
            n = n.getParent();
        }

        return false;
    }
}

// References
// How to check if a state is solvable: https://massivealgorithms.blogspot.com/2015/06/how-to-check-if-instance-of-8-puzzle-is.html
// Inspiration for the algorithm (choice for the A* search and checking possible moves): https://github.com/alizeyn/blind-huristic-search
// Inspiration from the provided code on how to use the TypeWriter