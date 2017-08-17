import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by kk on 9/20/16.
 */
public class MoveTruck {
//    public Stack<Checkboard> stack = new Stack<>();
    public HashMap<Checkboard, Integer> hash;
    public Stack<String> path = new Stack<>();
    public int visitcount = 0;
    public static int MaxDepth = 100;
    Checkboard startnode;
    public void readFile(File f) throws FileNotFoundException {
        Scanner in = new Scanner(f);
        String tmp = "";
        while (in.hasNextLine()) {
            tmp += in.nextLine();
        }

        Checkboard checkboard = new Checkboard();
        checkboard.setBoard(tmp);
        checkboard.printBoard();
        startnode = checkboard;
//        Checkboard newone = new Checkboard();
//        newone.setBoard(new String(tmp));
//        System.out.print (startnode.equals(newone));
//        System.out.print (startnode.hashCode() == newone.hashCode());
    }
    public int IDDFS() {
        if (startnode == null) return -1;
        for (int dep = 0; dep < MaxDepth; dep++) {
            hash = new HashMap<>();
            hash.put(startnode, dep);
            if (DLS(startnode, dep)) {
                printPath();
                System.out.print("The total number of visited nodes is " + visitcount + '\n');
                //plus the last 2 step to move out the ice truck
                System.out.print("The cost of Move ice truck is " + (dep + 2) + "\n\n");
                return dep;
            }

        }
        System.out.print("Can't find the solution");
        return -1;
    }

    private void printPath() {
        //plus the last 2 step to move out the ice truck
        path.add("1 -> 1");
        path.add("1 -> 1");
        StringBuilder output = new StringBuilder(path.toString());
        System.out.println(output.toString());
    }


    public boolean DLS(Checkboard node, int depth) {
        visitcount++;
       if (depth == 0 && node.isAnswer())
           return true;
       else if (depth > 0) {
           for (int i = 0; i < node.elementsleng; i++) {
               //get the location of vehicle i
               char[] c = node.describe.substring(i * 5, i * 5 + 4).toCharArray();

               int leng = 2;
               if (c[0] == 'B') leng = 3;
               int x = c[2] - 'A', y = c[3] - '1';

               if (c[1] == 'H') {
                   if (y+leng < 6 && !node.board[x][y + leng]) {
                       Checkboard nextnode = new Checkboard();
                       StringBuffer nextstring = new StringBuffer(node.describe);
                       nextstring.setCharAt(5*i+3,(char)('1' + y + 1));
                       nextnode.setBoard(nextstring.toString());
                       Integer old = hash.get(nextnode);
                       // check the node if visited. If there is a quick way , update
                       if (old == null || old < depth){
                           hash.put(nextnode, depth);
                           path.add((char)(i + '1') + " -> " + '1');
                           if (DLS(nextnode, depth - 1)) return true;
                           path.pop();
                       }
                   }
                   if (y - 1 >= 0 && !node.board[x][y - 1]) {

                       Checkboard nextnode = new Checkboard();
                       StringBuffer nextstring = new StringBuffer(node.describe);
                       nextstring.setCharAt(5 * i + 3, (char) ('1' + y - 1));
                       nextnode.setBoard(nextstring.toString());
                       Integer old = hash.get(nextnode);
                       if (old == null || old < depth) {
                           hash.put(nextnode, depth);
                           path.add((char) (i + '1') + " <- " + '1');
                           if (DLS(nextnode, depth - 1)) return true;
                           path.pop();
                       }
                   }
               }

               else if (c[1] == 'V') {
                   if (x+leng < 6 && !node.board[x+leng][y]) {
                       Checkboard nextnode = new Checkboard();
                       StringBuffer nextstring = new StringBuffer(node.describe);
                       nextstring.setCharAt(5*i+2,(char)('A' + x + 1));
                       nextnode.setBoard(nextstring.toString());
                       Integer old = hash.get(nextnode);
                       if (old == null || old < depth){
                           hash.put(nextnode, depth);
                           path.add((char)(i + '1') + " ↓ " + '1');
                           if (DLS(nextnode, depth - 1)) return true;
                           path.pop();
                       }
                   }
                   if (x-1 >= 0 && !node.board[x-1][y]) {
                       Checkboard nextnode = new Checkboard();
                       StringBuffer nextstring = new StringBuffer(node.describe);
                       nextstring.setCharAt(5*i+2,(char)('A' + x-1));
                       nextnode.setBoard(nextstring.toString());
                       Integer old = hash.get(nextnode);
                       if (old == null || old < depth){
                           hash.put(nextnode, depth);
                           path.add((char)(i + '1') + " ↑ " + '1');
                           if (DLS(nextnode, depth - 1)) return true;
                           path.pop();
                       }
                   }
               }
           }
       }

        return false;
    }

}
