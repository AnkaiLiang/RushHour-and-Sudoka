import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by kk on 9/30/16.
 */
public class main {
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("./src/input.txt");
        Sudoku sudoku = new Sudoku();
        sudoku.setBoard(f);
        sudoku.figureout();
    }
}
