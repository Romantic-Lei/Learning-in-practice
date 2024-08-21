package leetcode.leetcode150.array;

public class ReverseString151 {

    /**
     * https://leetcode.cn/problems/reverse-words-in-a-string/description/?envType=study-plan-v2&envId=top-interview-150
     * 给你一个字符串 s ，请你反转字符串中 单词 的顺序。
     *
     * 单词 是由非空格字符组成的字符串。s 中使用至少一个空格将字符串中的 单词 分隔开。
     *
     * 返回 单词 顺序颠倒且 单词 之间用单个空格连接的结果字符串。
     *
     * 注意：输入字符串 s中可能会存在前导空格、尾随空格或者单词间的多个空格。返回的结果字符串中，单词间应当仅用单个空格分隔，且不包含任何额外的空格。
     * @param s
     * @return
     */
    public static String reverseWords(String s) {
        String[] split = s.trim().split("\\s+");
        int i = 0;
        int j = split.length - 1;
        String temp;
        while (i < j) {
            temp = split[j];
            split[j] = split[i];
            split[i] = temp;
            i++;
            j--;
        }
        return String.join(" ", split);
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("the sky         is blue"));
    }

}
