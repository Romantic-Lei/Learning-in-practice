package leetcode.leetcode150.twopointers;

public class Palindrome125 {

    /**
     * https://leetcode.cn/problems/valid-palindrome/description/?envType=study-plan-v2&envId=top-interview-150
     *
     * 125. 验证回文串
     * 如果在将所有大写字符转换为小写字符、并移除所有非字母数字字符之后，短语正着读和反着读都一样。则可以认为该短语是一个 回文串 。
     * 字母和数字都属于字母数字字符。
     * 给你一个字符串 s，如果它是 回文串 ，返回 true ；否则，返回 false 。
     * @param s
     * @return
     */
    public static boolean isPalindrome(String s) {
        s = s.toLowerCase();
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            // 从左往右找，找到字母或者数字
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            // 从右往左找，找到字母或者数字
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // 汇合了，返回true
            if (left == right)
                return true;
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        String s = " ";
        System.out.println(isPalindrome(s));
    }
}
