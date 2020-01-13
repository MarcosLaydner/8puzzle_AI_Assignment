import java.io.FileNotFoundException;

public class Solver {

    private static int[] INITIAL_STATE = {8,7,6,5,4,3,2,1,0};

    public static void main(String[] args) throws FileNotFoundException {

        Searcher.search(INITIAL_STATE);

    }
}
