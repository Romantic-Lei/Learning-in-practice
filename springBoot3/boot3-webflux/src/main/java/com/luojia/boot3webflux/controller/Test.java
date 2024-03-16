package com.luojia.boot3webflux.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Test {
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

    public static boolean isMatch(String s, String p) {
        // char[] cs = s.toCharArray();
        // char[] cp = p.toCharArray();
        char[] cs = "aa".toCharArray();
        char[] cp = "a*".toCharArray();

        // dp[i][j] 表示s的前i部分和p的前j部分是否匹配
        boolean[][] dp = new boolean[cs.length+1][cp.length+1];
        dp[0][0] = true;

        for (int i = 1; i <= cs.length; i++) {
            for (int j = 1; j <= cp.length; j++) {
                // 判断第i-1部分和j-1部分是否能匹配上,或者j-1是.符号
                if (cs[i-1] == cp[j-1] || cp[j-1] == '.') {
                    dp[i][j] = dp[i-1][j-1];
                } else if (cp[j-1] == '*') {
                    if (cs[i-1] == cp[j-2] || cp[j-2] == '.') {
                        dp[i][j] = dp[i][j-2]     // 重复0次
                                || dp[i-1][j];    // 匹配1次或多次
                    } else {
                        dp[i][j] = dp[i][j-2];      // 模式串*的前一个字符不能够跟文本串的末位匹配,只能0次
                    }
                }
            }
        }
        return dp[cs.length][cp.length];
    }

    // 字符串转换整数 (atoi)
    public static int myAtoi(String s) {
        String newStr = s.trim();
        char[] chars = newStr.toCharArray();
        // 标志位，标记正数还是负数
        int flag = 1;
        // 判断是否出现过符号，判断字符串中 +-21这样的情况，同时排除出现43-8这样的字符串
        boolean signFlag = false;
        int res = 0;
        for (char c : chars) {
            if (c == '+' && !signFlag) {
                signFlag = true;
                continue;
            } else if (c == '-' && !signFlag) {
                flag = -1;
                signFlag = true;
                continue;
            } else if(c < '0' || c > '9') {
                // 发现非数字，直接返回
                return res*flag;
            }

            signFlag = true;
            int tem = c - '0';
            // 判断加上该数字后是否会溢出
            if (1==flag) {
                if (res > Integer.MAX_VALUE/10 || (res == Integer.MAX_VALUE/10 && tem > Integer.MAX_VALUE%10)) {
                    return  Integer.MAX_VALUE;
                }
            } else {
                if (-res < Integer.MIN_VALUE/10 || (-res == Integer.MIN_VALUE/10 && -tem < Integer.MIN_VALUE%10)) {
                    return  Integer.MIN_VALUE;
                }
            }

            res =res*10+tem;
        }

        return res*flag;
    }

    // 力扣第六题：整数反转
    public static int reverse(int x) {
        int res = 0;
        while(x != 0) {
            // 获取到最后一位，负数取余后，余数仍旧是负数
            int tem = x % 10;
            // Integer.MAX_VALUE -> 2147483647
            // Integer.MIN_VALUE -> -2147483648
            // 正常进来的int最大值最高位肯定是1或者2，所以这里不需要判断 Integer.MAX_VALUE/10 + tem是否超过int范围
            if (res > Integer.MAX_VALUE/10 || res < Integer.MIN_VALUE/10) return 0;
            res = res*10+tem;
            x = x/10;
        }

        return res;
    }

    // 力扣第六题：N 字形变换
    public String convert(String s, int numRows) {
        if (numRows < 2) {
            return s;
        }
        ArrayList<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }

        int i = 0;
        // 标志位，当在第一行或者numRows行时，标志位取反
        int flag = -1;
        for (char c : s.toCharArray()) {
            StringBuilder stringBuilder = rows.get(i);
            stringBuilder.append(c);
            if (i == 0 || i == numRows - 1) flag = -flag;
            i += flag;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (StringBuilder row : rows) {
            stringBuilder.append(row);
        }

        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    // 最大回文子串
    public static String longestPalindrome(String s) {
        if (null == s || s.length() < 1) {
            return "";
        }
        int leftIndex = 0;
        int rightIndex = 0;
        int max = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            // 自己就是中心
            int len1 = expandAroundCenter(s, i, i);
            // 自己和相邻字母对称
            int len2 = expandAroundCenter(s, i, i + 1);
            max = Math.max(len1, len2);
            if (max > rightIndex - leftIndex) {
                leftIndex = i - (max - 1) / 2;
                rightIndex = i + max / 2;
            }
        }
        // babad
        return s.substring(leftIndex, rightIndex + 1);
    }

    public static int expandAroundCenter(String s, int letf, int right) {
        int l = letf;
        int r = right;
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            l--;
            r++;
        }
        // l和r都会多向左和右移动一位
        return r - l - 1;
    }

    // 寻找两个正序数组的中位数
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        // 两个数组的总长度
        int len = m + n;
        // 数组下标为0 开始
        int aStart = 0;
        int bStart = 0;
        // 中位数
        int left = 0;
        int right = 0;
        for (int i = 0; i <= len / 2; i++) {
            left = right;
            if (aStart < m && (bStart >= n || nums1[aStart] < nums2[bStart])) {
                right = nums1[aStart++];
            } else {
                right = nums2[bStart++];
            }
        }

        if (len % 2 == 0) {
            // 变成浮点数，需要÷2.0而不能是2
            return (left + right) / 2.0;
        }
        return right;

    }

    // 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度
    public static int lengthOfLongestSubstring(String s) {
        if (s.length() == 0) return 0;
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        int left = 0;
        int max = 0;

        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                // 将滑动框中首次出现的字母位置移出到框外，取max是为了防止abba这种让left倒退的异常
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }

            map.put(s.charAt(i), i);
            // 最长子串与（当前元素位置到滑动框的起始位置相减）比较
            max = Math.max(max, i - left + 1);
        }
        return max;
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
}
