import java.util.*;

public class codeTest {

    //Amazon codility
    //Task1
    public static int checkMinFlag(int[][] flag, int[][] A, int n, int m) {
        int[][] direct = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        int N = flag.length;
        int M = flag[0].length;
        int mintag = flag[n][m];
        for (int i = 0; i < 4; i++) {
            //System.out.println(n+direct[i][0]+" "+m+direct[i][1]);
            if (n + direct[i][0] < N && n + direct[i][0] >= 0 && m + direct[i][1] < M && m + direct[i][1] >= 0 && A[n][m] == A[n + direct[i][0]][m + direct[i][1]]) {
                int ff = flag[n + direct[i][0]][m + direct[i][1]];
                if (ff < mintag) mintag = ff;
            }
        }
        return mintag;
    }

    public static void setMinFlag(int[][] flag, int[][] A, int n, int m, int mintag) {
        int[][] direct = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        flag[n][m] = mintag;
        int N = flag.length;
        int M = flag[0].length;
        for (int i = 0; i < 4; i++) {
            if (n + direct[i][0] < N && n + direct[i][0] >= 0 && m + direct[i][1] < M && m + direct[i][1] >= 0 && A[n][m] == A[n + direct[i][0]][m + direct[i][1]]) {
                flag[n + direct[i][0]][m + direct[i][1]] = mintag;

            }
        }
    }

    public static int solutionMatrix(int[][] A) {
        // write your code in Java SE 8
        int num = 0;
        int N = A.length;
        int M = A[0].length;
        int flag[][] = new int[N][M];
        int index = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                flag[i][j] = index++;
            }
        }
        //System.out.println(index);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int minf = checkMinFlag(flag, A, i, j);
                setMinFlag(flag, A, i, j, minf);
                //System.out.print(minf+"  ");
            }
            //System.out.println();
        }
        Set<Integer> ts = new TreeSet<Integer>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                ts.add(flag[i][j]);
            }
        }
        //System.out.println(ts.size());
        return ts.size();
    }

    //Task2
    public static int solutionList(int[] A) {
        // write your code in Java SE 8
        int res = 0;
        int tmp = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                tmp = A[i] + A[j] + j - i;
                if (tmp > res) {
                    res = tmp;
                }
            }
        }
        return res;
    }

    static class Tree {
        Tree(int X) {
            this.x = X;
            l = null;
            r = null;
        }

        Tree(int X, Tree l, Tree r) {
            this.x = X;
            this.l = l;
            this.r = r;
        }

        public int x;
        public Tree l;
        public Tree r;
    }

    //Task 3
    public static int recTree(Tree T, int maxI, int minI) {
        if (T.x < minI) minI = T.x;
        if (T.x > maxI) maxI = T.x;
        int templ = 0;
        int tempr = 0;
        if (T.l != null) {
            templ = recTree(T.l, maxI, minI);
        }
        if (T.r != null) {
            tempr = recTree(T.r, maxI, minI);
        }
        if (T.l == null & T.r == null) {
            return maxI - minI;
        }
        if (templ > tempr) return templ;
        return tempr;

    }

    public static int solutionTree(Tree T) {
        int max = T.x, min = T.x;
        int maxlen = recTree(T, max, min);
        return maxlen;
    }

    public static void main(String[] args) {
        //Task1
        int[][] A = {{5, 4, 4}, {4, 3, 4}, {3, 2, 4}, {2, 2, 2}, {3, 3, 4}, {1, 4, 4}, {4, 1, 1}};
        int countries = solutionMatrix(A);
        System.out.println(countries);
        //Task2
        int[] a = {4, 2, 73, 11, -5};
        int index = solutionList(a);
        System.out.println(index);
        //Task3
        Tree t1 = new Tree(1);
        Tree t3 = new Tree(3);
        Tree t7 = new Tree(7, t1, null);
        Tree t4 = new Tree(4, t3, null);
        Tree t9 = new Tree(9, t7, t4);

        Tree t2 = new Tree(2);
        Tree t12 = new Tree(12);
        Tree t8 = new Tree(8, t12, t2);

        Tree t5 = new Tree(5, t8, t9);

        System.out.println(solutionTree(t5));
    }
}
