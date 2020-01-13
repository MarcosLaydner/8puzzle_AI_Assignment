
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SolutionWriter {

    private int moves;
    private int expanded;
    private int unexpanded;
    private ArrayList<int[]> path;
    private String message;

    public SolutionWriter(int moves, int expanded, int unexpanded, ArrayList<int[]> path) {
        this.moves = moves;
        this.expanded = expanded;
        this.unexpanded = unexpanded;
        this.path = path;
        write();
    }

    public SolutionWriter() {
        this.message = "This Configuration is not solvable";
        write();
    }

    private void write() {
        File outFile = new File("output.txt");
        try {
            PrintWriter output = new PrintWriter(outFile);

            if (message == null) {
                for (int i = path.size()-1; i >= 0; i--) {

                    output.println(path.get(i)[0] + " | " + path.get(i)[1] + " | "
                            + path.get(i)[2]);
                    output.println("--+---+--");
                    output.println(path.get(i)[3] + " | " + path.get(i)[4] + " | "
                            + path.get(i)[5]);
                    output.println("--+---+--");
                    output.println(path.get(i)[6] + " | " + path.get(i)[7] + " | "
                            + path.get(i)[8]);
                    output.println("\n");

                }

                output.println(moves + " Moves");
                output.println(expanded + " Nodes expanded");
                output.println(unexpanded + " Nodes unexpanded");

            } else {
                output.println(message);
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
