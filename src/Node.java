
public class Node {

    private GridState curState;
    private Node parent;
    private double gCost; // greedy cost (total tile distance from goal)
    private double hCost; // heuristic cost (number of misplaced tiles)
    private double cost;  // overall cost (sum of both costs)

    public Node (GridState state) {
        this.curState = state;
        this.parent = null;
        this.gCost = 0;
        this.hCost = 0;
        this.cost = 0;
    }

    public Node(Node prev, GridState s, double c, double h) {
        parent = prev;
        curState = s;
        gCost = c;
        hCost = h;
        cost = gCost + hCost;
    }

    public GridState getCurState() {
        return curState;
    }

    public Node getParent() {
        return parent;
    }

    public double getCost() {
        return cost;
    }

    public double getGCost() {
        return gCost;
    }
}
