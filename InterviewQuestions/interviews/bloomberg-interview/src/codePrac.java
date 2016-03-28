import java.util.*;

public class codePrac {
    //Test interview

    /**
     *
     */
    public static int solution(int[] A) {
        int index = A.length / 2;
        Arrays.sort(A);
        return A[index];
    }

    public static int solution2(int[] A) {

        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < A.length; i++) {
            if (map.containsKey(A[i])) {
                map.put(A[i], map.get(A[i]).intValue() + 1);
            } else {
                map.put(A[i], 1);
            }
        }
        ArrayList<Integer> newList = new ArrayList<Integer>(map.values());
        Collections.sort(newList);
        int value = newList.get(newList.size() - 1);
        for (int o : map.keySet()) {
            if (map.get(o).equals(value)) {
                return o;
            }
        }
        return 0;
    }

    private static boolean contains(int[] A, int val) {
        for (int i = 0; i < A.length; i++) {
            if (A[i] == val)
                return true;

        }
        return false;
    }

    private static int size(int[] A) {
        int ctr = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] != -1) {
                ctr += A[i];
            }
        }
        return ctr;
    }

    public static int solution3(int[] A) {
        int len = A.length;
        if (len == 0)
            return 0;

        int max = -1, size = 0, tempVal = 0, ctr = 0;
        boolean cond = false;
        int[] numsA = new int[len];
        for (int i = 0; i < len; i++) {
            Arrays.fill(numsA, -1);
            ctr = 0;
            tempVal = i;
            while (ctr < len) {
                tempVal = A[tempVal];
                cond = (tempVal >= 0 && tempVal < len);
                if (!cond)
                    break;
                if (!contains(numsA, tempVal))
                    numsA[A[tempVal]]++;
                ctr++;
            }
            size = size(numsA);
            if (size > max)
                max = size;
        }
        return max;
    }


    public static void main(String[] args) {
        // write your code here
        int[] test = new int[]{1, 2, 5, 10, 20, 1};
        int[] test2 = new int[]{20, 10, 30, 30, 40, 10};
        int[] test3 = new int[]{5, 4, 0, 3, 1, 6, 2};
        System.out.println(solution(test));
        System.out.println(solution2(test2));
        System.out.println(solution3(test3));
    }
}
