import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martin on 15/11/2015.
 */
public class sampleTest {

    // Q1：
//    Given two lists of integers, write a function that returns a list that contains only the intersection
//    (elements that occur in both lists) of the two lists. The returned list should only contain unique integers,
//    no duplicates.For example, [4, 2, 73, 11, -5] and [-5, 73, -1, 9, 9, 4, 7]
//    would return the list [-5, 4, 73] in no particular order
    public static List mergeUnique(int[] a, int[] b) {
        List res = new ArrayList();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == b[j]) {
                    res.add(a[i]);
                }
            }
        }
        return res;
    }

//
//    Q2：
//    Given a binary tree, write a function that returns true if and only if it is a binary search tree.
//    Your solution will be evaluated on correctness, runtime complexity (big-O), and adherence to coding best practices.


//    Q3：
//    Find the K closest points to the origin in 2D plane, given an array containing N points.
//    You can assume K is much smaller than N and N is very large. You need only use standard math operators
//    (addition, subtraction, multiplication, and division).


//    Q4:
//    Find loop in the given linked list


//    Q5:
//    Give student result structure:
//    struct Result{
//        int studentID;
//        string data;
//        int testScore;
//    }
//
//    给一个result的vector，返回一个map<ID, highest 5 testScore>


//    Q6： Most frequent number in a list.


    //    Q7：Merge 2 sorted linkedList




    public static void main(String[] args) {
        // Q1
        int[] a = {4, 2, 73, 11, -5};
        int[] b = {-5, 73, -1, 9, 9, 4, 7};
        List res = mergeUnique(a, b);
        System.out.println(res);
    }
}
