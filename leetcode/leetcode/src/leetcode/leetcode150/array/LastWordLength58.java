package leetcode.leetcode150.array;

public class LastWordLength58 {

    /**
     * https://leetcode.cn/problems/length-of-last-word/?envType=study-plan-v2&envId=top-interview-150
     * 58. 最后一个单词的长度
     * 虽然有现成的API可以使用，但是也可以使用从后往前遍历的方式进行
     * @param s
     * @return
     */
    public static int lengthOfLastWord(String s) {
        s = s.trim();
        int length = 0;
        for (int i = s.length() - 1; i >= 0 ; i--) {
            if (' ' == (s.charAt(i)))
                return length;
            length++;
        }
        return length;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLastWord("Hello World"));
    }
}
