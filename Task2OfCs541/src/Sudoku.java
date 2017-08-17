import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * Created by kk on 9/30/16.
 */
public class Sudoku {
    public Cell[] cell = new Cell[81];
    public Constraint[] constraint = new Constraint[27];
    // Setting the board's init data and the relationship between constraints and cells
    public void setBoard(File f) throws FileNotFoundException {
        FileReader in = new FileReader(f);
        Scanner scan = new Scanner(in);
        int i = 0;
        while (scan.hasNextInt()) {
            cell[i] = new Cell(scan.nextInt(), i);
            i++;
        }

        i = 0;
        int[] p = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        for (int j = 0; j < 9; j++, i++) {
            constraint[i] = new Constraint(i);
            constraint[i].setCells(0, p, cell);
            for (int k = 0; k < p.length; k++) {
                p[k]+=9;
            }
        }

        p = new int[]{0, 9, 18, 27, 36, 45, 54, 63, 72};
        for (int j = 0; j < 9; j++, i++) {
            constraint[i] = new Constraint(i);
            constraint[i].setCells(1, p, cell);
            for (int k = 0; k < p.length; k++) {
                p[k]++;
            }
        }

        p = new int[]{0, 1, 2, 9, 10, 11, 18, 19, 20};
        for (int j = 0; j < 3; j++, i++) {
            constraint[i] = new Constraint(i);
            constraint[i].setCells(2, p, cell);
            for (int k = 0; k < p.length; k++) {
                p[k]+=3;
            }
        }

        p = new int[]{27, 28, 29, 36, 37, 38, 45, 46, 47};
        for (int j = 0; j < 3; j++, i++) {
            constraint[i] = new Constraint(i);
            constraint[i].setCells(2, p, cell);
            for (int k = 0; k < p.length; k++) {
                p[k]+=3;
            }
        }

        p = new int[]{54, 55, 56, 63, 64, 65, 72, 73, 74};
        for (int j = 0; j < 3; j++, i++) {
            constraint[i] = new Constraint(i);
            constraint[i].setCells(2, p, cell);
            for (int k = 0; k < p.length; k++) {
                p[k]+=3;
            }
        }



    }
    public void GenerateMsg_Constraint_to_Cell() {
        for (Constraint cons : constraint){
            cons.generateAllRmn();
        }

    }
    public void GenerateMsg_Cell_to_Constraint(){
        for (Cell ce : cell){
            ce.generateAllMsg();
        }

    }
    //fill in the board and sum the num of blanks
    public int update(){
        int blank = 0;
        for (Cell ce : cell){
            ce.update();
            if (ce.content == 0)
                blank++;
        }
        return blank;
    }

    public void init(){
        for (Cell ce : cell)
            ce.init();
    }

    public void printBoard(){
        System.out.print("----------------Split Line--------------------\n");
        int blanks = 0;
        for (int i = 0; i < 81; i++){
            System.out.print(cell[i].content);
            if (cell[i].content == 0) blanks++;
            System.out.print(' ');
            if ((i+1) % 9 == 0)
                System.out.print('\n');
        }
        System.out.println("There are " + blanks + " blanks");

    }

    public void printType(int x) {
        if (x == 0)
            System.out.print("Row: ");
        else if (x == 1)
            System.out.print("Col: ");
        else System.out.print("Box: ");
    }

    public void printA1Msg(){
        System.out.println("The A1 sent to its factor messages are:");
        for (int i = 0; i < 3; i++) {

            printType(i);
            printFloatArray(cell[0].sent_To_Cons[i]);
        }
        System.out.println("The messages that the factors sent to A1 are:");
        for (int i = 0; i < 3; i++) {

            printType(i);
            printFloatArray(cell[0].receive_From_Cons[i]);
        }
    }

    private void printFloatArray(float[] f) {
        for (int i = 1; i < f.length; i++) {
            System.out.print(f[i]);
            System.out.print(' ');
        }
        System.out.print('\n');
    }

    public void figureout() {
        printBoard();
        init();
        GenerateMsg_Constraint_to_Cell();
        printA1Msg();
        int flag = update();
        printBoard();

        //iteration begin
        while (flag > 0) {
            GenerateMsg_Cell_to_Constraint();
            GenerateMsg_Constraint_to_Cell();
            printA1Msg();
            int newflag = update();
            printBoard();
            //if nothing update stop iteration
            if (newflag == flag) break;
            flag = newflag;
        }

    }
}
