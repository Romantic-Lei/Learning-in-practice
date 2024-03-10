package leetcode;

public class leetcode10 {

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        // System.out.println(binary(arr, 2));
        int[] nums = new int[]{2, 7, 11, 15};
        int[] ints = twoSum(nums, 26);
        System.out.println("ints = " + ints[0] + "," + ints[1]);

        ListNode l1 = new ListNode(5);
        ListNode listNode = new ListNode(4);
        ListNode listNode1 = new ListNode(3);
        l1.next = listNode;
        listNode.next = listNode1;

        ListNode l2 = new ListNode(5);
        ListNode listNode2 = new ListNode(6);
        ListNode listNode3 = new ListNode(6);
        l2.next = listNode2;
        listNode2.next = listNode3;
        ListNode res = addTwoNumbers(l1, l2);

        // 543 566  --> 345 + 665 = 1010 --> 0101
        System.out.println(res.val + " " + res.next.val + " " + res.next.next.val + " " + res.next.next.next.val);

        System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));

        // longestPalindrome
        System.out.println("最大回文子串是：" + longestPalindrome("babad"));
        System.out.println("整数反转：" + reverse(-2147483641));

        System.out.println(myAtoi("   +-21"));

        System.out.println(isMatch("", ""));

    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
