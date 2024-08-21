package leetcode.leetcode150.array;

public class ZhishuH274 {

    /**
     * https://leetcode.cn/problems/h-index/description/?envType=study-plan-v2&envId=top-interview-150
     * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数。计算并返回该研究者的 h 指数。
     * 根据维基百科上 h 指数的定义：h 代表“高引用次数” ，一名科研人员的 h 指数 是指他（她）至少发表了 h 篇论文，
     * 并且 至少 有 h 篇论文被引用次数大于等于 h 。如果 h 有多种可能的值，h 指数 是其中最大的那个。
     * @param citations
     * @return
     */
    public int hIndex(int[] citations) {
        // 数组长度
        int n = citations.length;
        // 符合条件总数
        int total = 0;
        // counter 用来记录当前引用次数的论文有几篇，下标是几就代表引用次数
        int[] counter = new int[n + 1];

        // 将论文的引用次数统计出来
        for (int i = 0; i < n; i++) {
            if (citations[i] >= n) {
                counter[n]++;
            } else {
                counter[citations[i]]++;
            }
        }

        for (int i = counter.length - 1; i >= 0; i--) {
            // 符合条件的总数
            total = counter[i] + total;
            // 在数组 counter 中得到大于或等于当前引用次数 i 的总论文数
            if (total >= i) {
                return i;
            }
        }
        return 0;
    }
}
