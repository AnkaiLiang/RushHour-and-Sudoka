import java.util.HashSet;
/**
 * Created by kk on 9/30/16.
 */
// this is the node of cell
public class Cell {
    public int content = 0;
    public int index;
    public float[] pro = new float[10];
    public float[][] receive_From_Cons = new float[3][10];
    public float[][] sent_To_Cons = new float[3][10];
    public Constraint[] con = new Constraint[3];
    public HashSet<Integer> set= new HashSet<>();
    public Cell(int x, int num) {
        index = num;
        if (x != 0) {
            content = x;
            set.add(x);
            for (int i = 1; i < 10; i++) {
                pro[i] = 0;
            }
            pro[x] = 1;
        } else {
            for (int i = 1; i < 10; i++) {
                set.add(i);
                pro[i] = (float) (1.0 / 9.0);
            }
        }
    }
    public void generateAllMsg() {
        for (int n = 0; n < 3; n++) {
            float[] f = new float[10];
            for (Integer x : set) {
                float mult = 1;
                for (int i = 0; i < 3; i++) {
                    if (i == n) continue;
                    mult *= receive_From_Cons[i][x];
                }
                f[x] = mult;
            }
            normalize(f);
            sent_To_Cons[n] = f;
        }
    }

    public void init() {
        for (int i = 0; i < 3; i++)
            sent_To_Cons[i] = pro;
    }

    public void update(){
        if (content == 0){
            float[] f = new float[10];
            HashSet<Integer> newset = new HashSet<>();
            for (int x : set) {
                f[x] = receive_From_Cons[0][x] * receive_From_Cons[1][x] * receive_From_Cons[2][x];
            }
            normalize(f);
            pro = f;
            for (int x = 1; x < 10; x++){
                if (f[x] > 0) newset.add(x);
                if (f[x] == 1) {
                    content = x;
                    break;
                }
            }
            set = newset;
        }

    }

    private void normalize(float[] f) {
        float sum = 0;
        for (float x : f)
            sum += x;
        for (int i = 0; i < f.length; i++)
            f[i] = f[i] / sum;
    }


}
