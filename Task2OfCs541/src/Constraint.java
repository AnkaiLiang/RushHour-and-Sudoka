import java.util.HashSet;


/**
 * Created by kk on 9/30/16.
 */

// this is the node of constraint
public class Constraint {
    public int index;
    public Cell[] own_cells = new Cell[9];
    public HashSet<Integer> observed = new HashSet<>();
    public float globa_sum = 0;
    //type = 0,1,2 denote this constraint is row, col or box.
    public int type;
    public Constraint(int j) {
        index = j;
    }
    public void setCells(int type, int[] x, Cell[] cells){
        this.type = type;
        for (int i = 0; i < x.length; i++) {
            own_cells[i] = cells[x[i]];
            if (own_cells[i].content != 0)
                observed.add(own_cells[i].content);
        }
        for (int i = 0; i < own_cells.length; i++) {
            own_cells[i].con[type] = this;
        }
    }
    public void generateAllRmn() {
        for (int n = 0; n < 9; n++) {
            if (own_cells[n].content != 0){
                float[] f = new float[10];
                f[own_cells[n].content] = 1;
                own_cells[n].receive_From_Cons[type] = f;
                continue;
            }
            float[] f = new float[10];
            for (Integer x : own_cells[n].set) {
                HashSet<Integer> dup = new HashSet<>();
                dup.add(x);
                globa_sum = 0;
                //use the dfs to calculate the probability of each cell with each value
                caculate(own_cells[n].sent_To_Cons[type][x], 0, n, dup);
                dup.remove(x);
                f[x] = globa_sum;
            }
            normalize(f);
            //   r[n] = f;
            own_cells[n].receive_From_Cons[type] = f;
        }
    }

    private void normalize(float[] f) {
        float sum = 0;
        for (float x : f)
            sum += x;
        if (sum == 0) return;
        for (int i = 0; i < f.length; i++)
            f[i] = f[i] / sum;
    }

    private void caculate(float cur, int f1, int n, HashSet<Integer> dup) {
        if (f1 == n) caculate(cur, f1 + 1, n, dup);
        if (f1 >= 9) {
            globa_sum += cur;
            return;
        }
        for (Integer x : own_cells[f1].set) {
            if (dup.contains(x)) continue;
            dup.add(x);
            caculate(cur * own_cells[f1].sent_To_Cons[type][x], f1 + 1, n, dup);
            dup.remove(x);
        }
        return;
    }
}
