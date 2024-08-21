package leetcode.leetcode150.array;

public class Candy135 {

    /**
     * https://leetcode.cn/problems/candy/description/?envType=study-plan-v2&envId=top-interview-150
     * n 个孩子站成一排。给你一个整数数组 ratings 表示每个孩子的评分。
     *
     * 你需要按照以下要求，给这些孩子分发糖果：
     *
     * 每个孩子至少分配到 1 个糖果。
     * 相邻两个孩子评分更高的孩子会获得更多的糖果。
     * 请你给每个孩子分发糖果，计算并返回需要准备的 最少糖果数目 。
     *
     * @param ratings
     * @return
     */
    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] left = new int[n];
        left[0] = 1;
        // 从左到右遍历，选择糖果数量，右边的小于等于左边，都将其置为1
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i-1] < ratings[i]) {
                left[i] = left[i-1] + 1;
            } else {
                left[i] = 1;
            }
        }

        int temp = 1;
        int count = 0;
        // 还得从右到左遍历一次，因为有可能 【1,2,2,1】 【1,2,1,1】 很明显第三个和第四个糖果不符合要求
        // 左边的≤右边，置为1，和原始的比较，取最大值
        for (int i = ratings.length - 1; i >= 0 ; i--) {
            if (i < ratings.length - 1 && ratings[i] > ratings[i + 1]) {
                // 比右边大1即可
                temp++;
            } else {
                temp = 1;
            }

            count += Math.max(temp, left[i]);
        }

        return count;
    }

    public static void main(String[] args) {
        // int[] r = new int[]{1,3,2,2,1};
        int[] r = new int[]{1,2,2};
        candy(r);
    }

}
