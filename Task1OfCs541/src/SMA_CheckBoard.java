/**
 * Created by kk on 9/21/16.
 */
public class SMA_CheckBoard extends Checkboard{
    //f is the "depth + heuristic"
    public int f;
    //g is the depth
    public int g;
    public int count = 0;
    public SMA_CheckBoard father = null;
    //store the path which to this node
    public String path;
    public void SMA_setBoard(String tmp, int depth) {
        setBoard(tmp);
        g = depth;
        f = depth + caclulate_h();
    }

    public SMA_CheckBoard generateNextnode() {
        if (count == -1) return null;
        int index = ++count;
        SMA_CheckBoard node = this;
        for (int i = 0; i < node.elementsleng; i++) {
            char[] c = node.describe.substring(i * 5, i * 5 + 4).toCharArray();
            int leng = 2;
            if (c[0] == 'B') leng = 3;
            int x = c[2] - 'A', y = c[3] - '1';
            if (c[1] == 'H') {
                if (y + leng < 6 && !node.board[x][y + leng]) {
                    if (--index == 0){
                        SMA_CheckBoard nextnode = new SMA_CheckBoard();
                        StringBuffer nextstring = new StringBuffer(node.describe);
                        nextstring.setCharAt(5*i+3,(char)('1' + y + 1));
                        nextnode.SMA_setBoard(nextstring.toString(), g + 1);
                        nextnode.path = (char)(i + '1') + " -> " + "1";
                        nextnode.father = this;
                        return nextnode;
                    }
                }
                if (y-1 >= 0 && !node.board[x][y-1]) {
                    if (--index == 0){
                        SMA_CheckBoard nextnode = new SMA_CheckBoard();
                        StringBuffer nextstring = new StringBuffer(node.describe);
                        nextstring.setCharAt(5*i+3,(char)('1' + y-1));
                        nextnode.SMA_setBoard(nextstring.toString(), g + 1);
                        nextnode.path = (char)(i + '1') + " <- " + "1";
                        nextnode.father = this;
                        return nextnode;
                    }
                }
            }

            else if (c[1] == 'V') {
                if (x+leng<6 && !node.board[x + leng][y]) {
                    if (--index == 0) {
                        SMA_CheckBoard nextnode = new SMA_CheckBoard();
                        StringBuffer nextstring = new StringBuffer(node.describe);
                        nextstring.setCharAt(5*i+2,(char)('A' + x + 1));
                        nextnode.SMA_setBoard(nextstring.toString(), g + 1);
                        nextnode.path = (char)(i + '1') + " ↓ " + "1";
                        nextnode.father = this;
                        return nextnode;
                    }
                }
                if (x-1 >=0 && !node.board[x-1][y]) {
                    if (--index == 0) {
                        SMA_CheckBoard nextnode = new SMA_CheckBoard();
                        StringBuffer nextstring = new StringBuffer(node.describe);
                        nextstring.setCharAt(5*i+2,(char)('A' + x-1));
                        nextnode.SMA_setBoard(nextstring.toString(), g + 1);
                        nextnode.path = (char)(i + '1') + " ↑ " + "1";
                        nextnode.father = this;
                        return nextnode;
                    }
                }
            }
        }
        count = -1;
        return null;
    }

    private int caclulate_h() {
        int h = 0;
        for (int i = icePosition + 2; i < 6; i++)
            if (board[2][i]) h+=1;
        h += 6 - icePosition;
        return h;
    }


}
