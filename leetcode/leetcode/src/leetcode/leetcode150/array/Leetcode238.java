package leetcode.leetcode150.array;

public class Leetcode238 {

    /**
     * https://leetcode.cn/problems/product-of-array-except-self/?envType=study-plan-v2&envId=top-interview-150
     * 238. 除自身以外数组的乘积
     * 给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
     *
     * 题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
     *
     * 请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
     *
     * 需要使用前缀积 和 后缀积
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        int pre = 1;

        // 前缀积
        for (int i = 0; i < n; i++) {
            ans[i] = pre;
            pre *= nums[i];
        }

        pre = 1;
        // 后缀积
        for (int i = n - 1; i >= 0; i--) {
            int temp = ans[i];
            ans[i] = temp * pre;
            pre = pre * nums[i];
        }

        return ans;
    }

}
