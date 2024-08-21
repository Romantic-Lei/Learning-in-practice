package leetcode.leetcode150.array;

public class Jieyushui42 {

    /**
     * https://leetcode.cn/problems/trapping-rain-water/?envType=study-plan-v2&envId=top-interview-150
     * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
     * @param height
     * @return
     */
    public static int trap(int[] height) {
        int length = height.length;
        int[] preMax = new int[length];
        int[] sufMax = new int[length];
        int ans = 0;
        preMax[0] = height[0];
        sufMax[length-1] = height[length-1];
        // 动态规划 寻找当前节点左边最高的柱子
        for (int i = 1; i < length; i++) {
            preMax[i] = Math.max(height[i], preMax[i-1]);
        }

        // 从右往左，寻找当前节点右边最高的柱子
        for (int i = length-2; i >= 0; i--) {
            sufMax[i] = Math.max(height[i], sufMax[i+1]);
        }

        // {4,2,0,3,2,5} 为例
        // preMax [4, 4, 4, 4, 4, 5]
        // sufMax [5, 5, 5, 5, 5, 5]

        for (int i = 0; i < length; i++) {
            // 取左右两边矮的柱子为边界，然后减去当前位置柱子高度
            ans += Math.min(preMax[i], sufMax[i]) - height[i];
        }

        return ans;
    }

    public static void main(String[] args) {
        int[] r = new int[]{4,2,0,3,2,5};
        System.out.println(trap(r));
    }

}
