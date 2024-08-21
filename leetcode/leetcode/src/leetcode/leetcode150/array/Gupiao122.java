package leetcode.leetcode150.array;


public class Gupiao122 {

    /**
     * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/?envType=study-plan-v2&envId=top-interview-150
     * 122. 买卖股票的最佳时机 II
     * 贪心算法
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            int temp = prices[i] - prices[i - 1];
            if (temp > 0) {
                profit = temp + profit;
            }
        }
        return profit;
    }
}
