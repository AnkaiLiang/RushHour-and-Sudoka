import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by kk on 9/21/16.
 */
public class SMA_MoveTruck {
    //Set the limit memory length.
    private static final int MAX_QUEUE_LENGTH = 5000;
    int NumofVisitNode = 0;
    SMA_CheckBoard startnode;
    // set = CLOSE Table
    HashSet<SMA_CheckBoard> set = new HashSet<>();
    // heap = OPEN Table
    // use heap to store the candidate node.
    Queue<SMA_CheckBoard> heap = new PriorityQueue<>(MAX_QUEUE_LENGTH, new Comparator<SMA_CheckBoard>() {
        @Override
        public int compare(SMA_CheckBoard o1, SMA_CheckBoard o2) {
            return o1.f - o2.f;
        }
    });

    public void readFile(File f) throws FileNotFoundException {
        Scanner in = new Scanner(f);
        String tmp = "";
        while (in.hasNextLine()) {
            tmp += in.nextLine();
        }

        SMA_CheckBoard checkBoard = new SMA_CheckBoard();
        checkBoard.SMA_setBoard(tmp, 0);
        checkBoard.printBoard();
        startnode = checkBoard;
        heap.add(startnode);
    }

    public void SMA_Search(){
        while (!heap.isEmpty()){
            SMA_CheckBoard bestNode = heap.peek();
            NumofVisitNode++;
            if (bestNode.isAnswer()){
                printepath(bestNode);
                System.out.print("The cost of Move ice truck is " + (bestNode.g + 2)+ '\n');
                System.out.print("The total number of visited nodes is " + NumofVisitNode + '\n');
                return;
            }

            SMA_CheckBoard nextNode = bestNode.generateNextnode();
            if (nextNode == null) {
                set.add(heap.poll());
                continue;
            }
            if (heap.contains(nextNode)){
                for (Iterator iter = heap.iterator(); iter.hasNext();) {
                    SMA_CheckBoard tmp = (SMA_CheckBoard) iter.next();
                    if (tmp.equals(nextNode) && tmp.g > nextNode.g){
                        tmp.g = nextNode.g;
                        tmp.father = bestNode;
                        tmp.path = nextNode.path;
                        tmp.f = nextNode.f;
                        tmp.count = 0; // reset the generator
                    }
                }
            } else if (set.contains(nextNode)) {
                for (Iterator iter = set.iterator(); iter.hasNext();) {
                    SMA_CheckBoard tmp = (SMA_CheckBoard) iter.next();
                    if (tmp.equals(nextNode) && tmp.g > nextNode.g){
                        tmp.g = nextNode.g;
                        tmp.father = bestNode;
                        tmp.path = nextNode.path;
                        tmp.f = nextNode.f;
                        tmp.count = 0; // reset the generator
                        heap.add(tmp);
                        iter.remove();
                    }
                }
            } else heap.add(nextNode);
        }
        System.out.print("Can't find the solution.\n");
    }

    private void printepath(SMA_CheckBoard bestNode) {
        StringBuilder str = new StringBuilder(bestNode.path);
        SMA_CheckBoard tmp = bestNode.father;
        while (tmp.father != null) {
            str.insert(0, tmp.path + ", ");
            tmp = tmp.father;
        }
        str = str.append(", 1 -> 1, 1 -> 1");
        System.out.print(str.toString() + '\n');
    }

}
