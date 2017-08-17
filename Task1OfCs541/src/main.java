import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by kk on 9/20/16.
 */
public class main {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("src/input.txt");
        // Use the DFS to deal with the problem
        System.out.print("By Deep-First-Search:\n");

        MoveTruck moveTruck = new MoveTruck();
        moveTruck.readFile(f);
        moveTruck.IDDFS();

        // Use the SMA* to deal with the problem
        System.out.print("By SMA*:\n");

        SMA_MoveTruck sma_moveTruck = new SMA_MoveTruck();
        sma_moveTruck.readFile(f);
        sma_moveTruck.SMA_Search();
    }
}
