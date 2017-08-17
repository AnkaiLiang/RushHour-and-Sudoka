
import java.util.Objects;

/**
 * Created by kk on 9/20/16.
 */
public class Checkboard {
    public boolean[][] board;
    public int icePosition;
    //elemetsleng = the number of vehicle
    public int elementsleng;
    public String describe;
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (getClass() != o.getClass()) return false;
        Checkboard other = (Checkboard) o;
        return describe.equals(other.describe);
    }

    public int hashCode() {
        return Objects.hash(describe);
    }

    public void setBoard(String tmp) {
        board = new boolean[6][6];
        String[] e = tmp.split(",");
        elementsleng = e.length;
        describe = tmp;
        for (String s : e) {
            if (s == null || s.length() == 0) break;
            char[] c = s.toCharArray();
            if (c[0] == 'I') {
                icePosition = c[3] - '1';
                board[2][icePosition] = true;
                board[2][icePosition + 1] = true;
            } else {
                int leng = 0;
                if (c[0] == 'C')
                    leng = 2;
                else if (c[0] == 'B')
                    leng = 3;

                int x = c[2] - 'A';
                int y = c[3] - '1';
                if (c[1] == 'H') {
                    for (int i = 0; i < leng; i++)
                        board[x][y+i] = true;
                }else if (c[1] == 'V')
                    for (int i = 0; i < leng; i++)
                        board[x+i][y] = true;
            }
        }
    }
    public boolean isAnswer() {
        if (icePosition == 4) return true;
        return false;
    }

    public void printBoard() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j])
                    System.out.print('*');
                else System.out.print('-');
            }
            System.out.print('\n');
        }
        System.out.print("The ice chuck position is C" + (char) ('1' + icePosition) + '\n');
    }
}
