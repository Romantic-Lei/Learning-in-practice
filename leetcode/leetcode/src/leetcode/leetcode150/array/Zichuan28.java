package leetcode.leetcode150.array;

public class Zichuan28 {

    /**
     * https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/?envType=study-plan-v2&envId=top-interview-150
     *
     * 给你两个字符串 haystack 和 needle ，请你在 haystack 字符串中找出 needle 字符串的第一个匹配项的下标（下标从 0 开始）。
     * 如果 needle 不是 haystack 的一部分，则返回  -1 。
     *
     * KMP算法
     * KMP算法核心就是构建出 next数组
     * 答案题解：
     * https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/solutions/575568/shua-chuan-lc-shuang-bai-po-su-jie-fa-km-tb86/?envType=study-plan-v2&envId=top-interview-150
     *
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {
        if(needle.isEmpty())
            return 0;

        int n = haystack.length(), m = needle.length();
        // 声明next数组的长度
        int next[] = new int[m+1];
        haystack = " " + haystack;
        needle = " " + needle;

        char[] haystackChars = haystack.toCharArray();
        char[] needleChars = needle.toCharArray();

        // 维护next 数组
        for (int i = 2, j = 0; i <= m; i++) {
            // 如果当前字符串不匹配，则找next数组中j下标的前一位
            while (j != 0 && needleChars[j + 1] != needleChars[i]) j = next[j];

            // 如果相等，j后移
            if (needleChars[j + 1] == needleChars[i]) j++;
            next[i] = j;
        }

        // 真正开始遍历
        for (int i = 1, j = 0; i <= n; i++) {
            // 原串和模式串匹配不上，则需要移动模式串的比较位置
            while (j > 0 && haystackChars[i] != needleChars[j + 1]) j = next[j];

            // 如果对应位置的字符串匹配上了，j和i都需要后移
            if (haystackChars[i] == needleChars[j + 1]) j++;
            // 如果j等于字符串长度，则已经完全匹配上了，返回结果
            if (j == m) return i - m;
        }
        // 没有匹配上，返回-1
        return -1;
    }

    public static void main(String[] args) {
        // System.out.println(strStr("leetcode", "leeto"));
        System.out.println(strStr("mississippi", "issip"));
    }
}
