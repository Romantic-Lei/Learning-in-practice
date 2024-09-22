package leetcode.leetcode150.slidingwindow;

import java.util.*;

public class FindSubstring30 {

    /**
     * https://leetcode.cn/problems/substring-with-concatenation-of-all-words/?envType=study-plan-v2&envId=top-interview-150
     * 30. 串联所有单词的子串
     * 给定一个字符串 s 和一个字符串数组 words。 words 中所有字符串 长度相同。
     *  s 中的 串联子串 是指一个包含  words 中所有字符串以任意顺序排列连接起来的子串。
     * 例如，如果 words = ["ab","cd","ef"]， 那么 "abcdef"， "abefcd"，"cdabef"， "cdefab"，"efabcd"， 和 "efcdab" 都是串联子串。 "acdbef" 不是串联子串，因为他不是任何 words 排列的连接。
     * 返回所有串联子串在 s 中的开始索引。你可以以 任意顺序 返回答案。
     *
     * 解析：
     * 图解：https://leetcode.cn/problems/substring-with-concatenation-of-all-words/?envType=study-plan-v2&envId=top-interview-150
     * 代码解析：https://leetcode.cn/problems/substring-with-concatenation-of-all-words/solutions/1616997/chuan-lian-suo-you-dan-ci-de-zi-chuan-by-244a/?envType=study-plan-v2&envId=top-interview-150
     * @param s
     * @param words
     * @return
     */
    public static List<Integer> findSubstring(String s, String[] words) {
        // List<Integer> res = new ArrayList<Integer>();
        Set<Integer> resSet = new HashSet<>();
        // words 中字符串个数
        int wordNum = words.length;
        if (wordNum == 0) {
            return new ArrayList<Integer>();
        }

        // 字符串长度
        int wordLen = words[0].length();

        for (int i = 0; i < wordLen; i++) {
            // 字符串出现次数集合
            Map<String, Integer> wordNumMap = new HashMap<>();
            // 遍历 words 中每个字符串出现的次数
            for (String word : words) {
                // 保存每个字符串出现的次数
                wordNumMap.put(word, wordNumMap.getOrDefault(word, 0) + 1);
            }

            // 左边界,每轮滑动窗口的起始位置
            int left = i;
            // 右边界，每轮滑动窗口的结束位置
            int right = i;
            // 当前匹配上的单词数量
            int curCount = 0;
            while (right <= s.length() - wordLen) {
                String str = s.substring(right, right + wordLen);
                Integer cnt = wordNumMap.getOrDefault(str, 0);
                if (cnt > 0) {
                    // 目标字符串数组中有该字符串并且剩余个数＞0
                    wordNumMap.put(str, cnt - 1);
                    curCount++;
                    right += wordLen;

                    // 如果长度和目标字符串数组长度相等表示找到了一个答案
                    if (wordNum == curCount) {
                        // res.add(left);
                        resSet.add(left);
                    }
                } else {
                    // 当前字符串在目标字符串数组中不存在或者超过了次数限制
                    if (curCount > 0) {
                        // 如果之前匹配上了一部分，现在需要从最开始慢慢移除，
                        // 直到当前字符串出现次数和目标字符串数组允许的次数一致即可
                        String preStr = s.substring(left, left + wordLen);
                        wordNumMap.put(preStr, wordNumMap.get(preStr) + 1);
                        left += wordLen;
                        curCount--;
                    } else {
                        // 当前匹配上的单词数量为0，那么left 和 right 重合了，
                        // 此时需要将窗口起始和结束后移
                        left += wordLen;
                        right += wordLen;
                    }
                }
            }
        }

        List<Integer> res = new ArrayList<Integer>(resSet);
        return res;
    }

    public static void main(String[] args) {
        String s = "abababab";
        String[] words = new String[]{"ab","ba"};
        System.out.println(findSubstring(s, words));
    }
}
