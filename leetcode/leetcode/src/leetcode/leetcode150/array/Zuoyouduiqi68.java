package leetcode.leetcode150.array;

import java.util.ArrayList;
import java.util.List;

public class Zuoyouduiqi68 {

    /**
     * https://leetcode.cn/problems/text-justification/description/?envType=study-plan-v2&envId=top-interview-150
     * 给定一个单词数组 words 和一个长度 maxWidth ，重新排版单词，使其成为每行恰好有 maxWidth 个字符，且左右两端对齐的文本。
     * 你应该使用 “贪心算法” 来放置给定的单词；也就是说，尽可能多地往每行中放置单词。必要时可用空格 ' ' 填充，使得每行恰好有 maxWidth 个字符。
     * 要求尽可能均匀分配单词间的空格数量。如果某一行单词间的空格不能均匀分配，则左侧放置的空格数要多于右侧的空格数。
     * 文本的最后一行应为左对齐，且单词之间不插入额外的空格。
     * 注意:
     * 单词是指由非空格字符组成的字符序列。
     * 每个单词的长度大于 0，小于等于 maxWidth。
     * 输入单词数组 words 至少包含一个单词。
     *
     * https://leetcode.cn/problems/text-justification/solutions/986756/wen-ben-zuo-you-dui-qi-by-leetcode-solut-dyeg/?envType=study-plan-v2&envId=top-interview-150
     * @param words
     * @param maxWidth
     * @return
     */
    public static List<String> fullJustify(String[] words, int maxWidth) {
        List<String> ans = new ArrayList<String>();
        int right = 0, n = words.length;
        while (true) {
            int left = right; // 每一行的第一个字母
            int sumLen = 0;// 改行单词总长度
            // 找到每一行最多能放下的单词，当right小于数组总长度并且当前单词长度+该行总长度+（right - left）即空格个数小于maxWidth
            while (right < n && sumLen + words[right].length() + right - left <= maxWidth) {
                sumLen += words[right].length();
                right++;
            }

            // 当前为最后一行
            if (right == n) {
                StringBuilder sb = join(words, left, right, " ");
                // maxWidth - sumLen 是减去所有单词字母长度
                // 接着减去 (right - left - 1) 是每个字母都会间隔一个空格，即减掉已经产生的空格
                sb.append(blank(maxWidth - sumLen - (right - left - 1)));
                ans.add(sb.toString());
                return ans;
            }

            // 当前行只有一个单词
            if (right - left == 1) {
                StringBuilder sb = new StringBuilder();
                sb.append(words[left]).append(blank(maxWidth - sumLen));
                ans.add(sb.toString());
                continue;
            }

            // 不止一个单词时
            // 查看有多长还剩下多少字符位
            int numSpaces = maxWidth - sumLen;
            // 因为最后一个单词需要右对齐，所以需要排除分配空格，需要-1，求出每个字母应该加上的空格
            int avgSpaces = numSpaces / (right - left - 1);
            // 余下的前面几个字符分，即余下6个空格，前面的6个单词多一个空格
            int extraSpaces = numSpaces % (right - left - 1);
            StringBuilder sb = new StringBuilder();
            // 前面的 extraSpaces 个单词多一个空格
            sb.append(join(words, left, left + extraSpaces + 1, blank(avgSpaces + 1)));
            sb.append(blank(avgSpaces));
            sb.append(join(words, left + extraSpaces + 1, right, blank(avgSpaces)));
            ans.add(sb.toString());
        }

    }

    private static String blank(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * 字符串拼接
     * @param words 支付串数组
     * @param left  开始截取的字符串下标
     * @param right 接受截取的字符串下标  应该right在上面遍历的时候，下标多移动了一位，所以这里排除掉
     * @param sep   补齐字符
     * @return
     */
    private static StringBuilder join(String[] words, int left, int right, String sep) {
        StringBuilder sb = new StringBuilder();
        sb.append(words[left]);
        for (int i = left + 1; i < right; i++) {
            sb.append(sep).append(words[i]);
        }

        return sb;
    }

    public static void main(String[] args) {
        // String[] worlds = new String[]{"This", "is", "an", "example", "of", "text", "justification."};
        String[] worlds = new String[]{"What","must","be","acknowledgment","shall","be"};

        List<String> strings = fullJustify(worlds, 16);
        strings.forEach(System.out::println);
        // "shall be        ";
        // "shall be        "
        // "shall be         "
    }

}
