package leetcode.leetcode150.array;

public class Tiaoyue55 {

    /**
     * https://leetcode.cn/problems/jump-game/?envType=study-plan-v2&envId=top-interview-150
     * 55. 跳跃游戏
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int k = 0;
        for (int i = 0; i < nums.length; i++) {
            if (k < i) {
                return false;
            }
            k = Math.max(k, i + nums[i]);
        }
        return true;
    }
}
