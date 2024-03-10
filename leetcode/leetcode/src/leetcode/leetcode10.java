package leetcode;

import java.util.HashMap;

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

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int carry = 0;

        while (l1 != null || l2 != null) {
            int x = l1 == null ? 0 : l1.val;
            int y = l2 == null ? 0 : l2.val;
            int sum = x + y + carry;

            carry = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);

            cur = cur.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }

            if (carry != 0) {
                cur.next = new ListNode(carry);
            }

        }

        return pre.next;
    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            int tmp = target - nums[i];
            if (map.containsKey(tmp)) {
                return new int[]{i, map.get(tmp)};
            }
            map.put(nums[i], i);
        }

        return new int[]{-1, -1};
    }

    // 返回坐标
    public static int binary(int arr[], int tarNum) {
        int low = 0;
        int high = arr.length;
        int middle = 0;

        if (low == high || tarNum < arr[0] || tarNum > arr[high - 1]) {
            return -1;
        }

        while (low <= high - 1) {
            middle = (low + high - 1) / 2;
            if (tarNum > arr[middle]) {
                low = middle + 1;
            } else if (tarNum < arr[middle]) {
                high = middle - 1;
            } else {
                return middle;
            }
        }

        return -1;
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
