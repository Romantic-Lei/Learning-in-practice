package leetcode.leetcode150.array;

public class Gupiao121 {

    /**
     * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/?envType=study-plan-v2&envId=top-interview-150
     * 121. 买卖股票的最佳时机
     * 给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。
     *
     * 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。
     *
     * 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {
        int maxprice = 0;
        int minprice = Integer.MAX_VALUE;

        for (int price : prices) {
            minprice = Math.min(minprice, price);
            maxprice = Math.max(maxprice, price - minprice);
        }
        return maxprice;
    }

    public static void main(String[] args) {
        int[] prices = new int[]{7,1,5,3,6,4};
        maxProfit(prices);
    }

}
