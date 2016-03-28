import java.util.LinkedList;

/**
 * Created by Martin on 18/02/2016.
 */
public class main {
    /**
     * 1. Describe recursion to a 5 year old child
     * Show them a picture of a painter who is painting a picture of painter who is painting a picture...
     *
     *
     * 2. black and white box, etc.
     * White-box testing: is a method of testing software that tests internal structures or workings of an application,
     * as opposed to its functionality
     *
     * Black-box testing is a method of software testing that examines the functionality of an application
     * without peering into its internal structures or workings.
     */

    /**
     * Design an algorithm for parsing code to check whether all the brackets and parentheses match.
     *
     * @param S
     * @return
     */
    public static int brackets(String S) {
        LinkedList<Character> stack = new LinkedList<>();
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == '{' || c == '[' || c == '(') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return 0;
                }
                char corresponding = stack.pop();
                if (c == ')' && corresponding != '(') {
                    return 0;
                }
                if (c == ']' && corresponding != '[') {
                    return 0;
                }
                if (c == '}' && corresponding != '{') {
                    return 0;
                }
            }
        }
        return stack.isEmpty() ? 1 : 0;
    }

    /**
     * reverse the order of words in a string.
     *
     * @param string
     * @return
     */
    public static String reverseWord(String string) {
        String str[] = string.split(" ");
        String res = "";
        for (int i = str.length - 1; i >= 0; i--) {
            res += str[i] + " ";
        }
        return res;
    }

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }

    /**
     * Design a function to reverse a linked list.
     * Thoughts:
     * Cut off everything from [2 ~ ] and save it in cutoff;
     * Append old reversed list to current head. Make itself as the new reversedList.
     * Basically: append the 1st element to head of the reversedList, like a stack.
     * Save head = cutOff: basically moves on to next element.
     *
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode reversedList = null;
        while (head != null) {
            ListNode cutOff = head.next;
            head.next = reversedList;
            reversedList = head;
            head = cutOff;
        }
        return reversedList;
    }

    /**
     * Remove Nth from the End
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return null;

        //get length of list
        ListNode p = head;
        int len = 0;
        while (p != null) {
            len++;
            p = p.next;
        }
        //if remove first node
        int fromStart = len - n + 1;
        if (fromStart == 1)
            return head.next;
        //remove non-first node
        p = head;
        int i = 0;
        while (p != null) {
            i++;
            if (i == fromStart - 1) {
                p.next = p.next.next;
            }
            p = p.next;
        }

        return head;
    }

    /**
     * Check prime.
     *
     * @param n
     * @return
     */
    public boolean isPrime(int n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    /**
     * Longest Common Sub string.
     *
     * @param A
     * @param B
     * @return
     */
    public int longestCommonSubstring(String A, String B) {
        // write your code here
        int max = 0;
        int[][] d = new int[A.length() + 1][B.length() + 1];

        for (int i = 0; i < A.length(); i++) {
            for (int j = 0; j < B.length(); j++) {
                if (A.charAt(i) == B.charAt(j)) {
                    d[i + 1][j + 1] = d[i][j] + 1;
                    max = Math.max(max, d[i + 1][j + 1]);
                }
            }
        }
        return max;
    }

    /**
     * binary search
     * @param a
     * @param low
     * @param high
     * @param searchValue
     * @return
     */
    public static int BinarySearch(int[] a, int low, int high, int searchValue) {
    // recursive version
        int mid;
        if (high <= low)
            return -1;
        mid = (low + high) >>> 1; // Or mid = low + ((high - low) / 2)
        if (a[mid] > searchValue) {
            return BinarySearch(a, low, mid, searchValue);
        } else if (a[mid] < searchValue) {
            return BinarySearch(a, mid + 1, high, searchValue);
        } else //when a[mid] is the search value..
        {
            return mid;
        }
    } //end function

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(brackets("{[()()]}"));
        System.out.println(reverseWord("He is the one"));

        ListNode test = new ListNode(1);
        test.next = new ListNode(2);
        test.next.next = new ListNode(3);
        test = reverseList(test);
        while(test != null) {
            System.out.println(test.val);
            test = test.next;
        }
    }
}
