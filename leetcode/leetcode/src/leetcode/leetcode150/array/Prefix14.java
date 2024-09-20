package leetcode.leetcode150.array;

public class Prefix14 {

    /**
     * https://leetcode.cn/problems/longest-common-prefix/?envType=study-plan-v2&envId=top-interview-150
     * 14. 最长公共前缀
     * 因为是前缀，所以可以是只比较某某某开头的字符串
     * @param strs
     * @return
     */
    public static String longestCommonPrefix(String[] strs) {
        String firstStr = strs[0];
        for (String str : strs) {
            while (!str.startsWith(firstStr)) {
                // 前闭后开，firstStr 会每次减少一位
                firstStr = firstStr.substring(0, firstStr.length() - 1);
                if ("".equals(firstStr))
                    return "";
            }
        }
        return firstStr;
    }

    public static void main(String[] args) {
        String[] strs = new String[]{"dog","racecar","car"};
        System.out.println(longestCommonPrefix(strs));
    }

}
