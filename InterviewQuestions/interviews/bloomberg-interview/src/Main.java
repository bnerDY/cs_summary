import java.util.*;

/**
 * Created by Martin on 01/02/2016.
 */
public class Main {


    //    Find the missing integer in an array of integers.
    public static int findMissInt(int[] A) {
        int sum = 0;
        int total = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
        }
        total = (A[0] + A[A.length - 1]) * (A.length + 1) / 2;
        return total - sum;
    }

    //given an array of numbers, find the second largest numbers in the array
    public static int[] findTwoLargest(int[] A) {
        int[] res = new int[2];
        SortedSet<Integer> set = new TreeSet<Integer>();
        for (int i : A) {
            set.add(i);
        }
        // Remove the maximum value; print the largest remaining item
        res[0] = set.last();
        set.remove(set.last());
        res[1] = set.last();
//        System.out.println(set.last());
        return res;
    }

    //    the two-sum problem
    //    Given an array of integers and a number, print all the pairs which would add up to that number.
    public static int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int[] result = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(numbers[i])) {
                result[0] = map.get(numbers[i]) + 1;
                result[1] = i + 1;
                break;
            } else {
                map.put(target - numbers[i], i);
            }
        }
        return result;
    }
//    Remove duplicates from an unsorted singly linked list.


//  Coding question: reverse a singly linked list
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null)
            return head;

        //get second node
        ListNode second = head.next;
        //set first's next to be null
        head.next = null;

        ListNode rest = reverseList(second);
        second.next = head;

        return rest;
    }

    // maximum subarray
    public int maxSubArray(int[] A) {
        int newsum = A[0];
        int max = A[0];
        for (int i = 1; i < A.length; i++) {
            newsum = Math.max(newsum + A[i], A[i]);
            max = Math.max(max, newsum);
        }
        return max;
    }

//    Implement a method to decide whether a string is palindrome or not.

    public static boolean isPalindrome(String s) {
        int n = s.length();
        for (int i = 0; i < (n / 2) + 1; i++) {
            if (s.charAt(i) != s.charAt(n - i - 1)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPalindrome1(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    public static void main(String[] args) {
        // write your code here
        int[] test = {-1, 0, 1, 2, 3, 4, 5, 6, 8, 9};
//        System.out.println(findMissInt(test));
        String temp = "abcd";
        System.out.println(isPalindrome(temp));
        for (int i = 0; i < findTwoLargest(test).length; i++) {
            System.out.println(findTwoLargest(test)[i]);
        }

        for (int i = 0; i < twoSum(test, 10).length; i++) {
            System.out.println(twoSum(test, 10)[i]);
        }

    }
}
