package leetcode.leetcode150.array;

public class Tiaoyue22 {

    /**
     * https://leetcode.cn/problems/jump-game-ii/?envType=study-plan-v2&envId=top-interview-150
     * 45. 跳跃游戏 II
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int skipNum = 0; // 跳跃次数
        int maxPos = 0; // 每次跳跃比较出跳的最远的位置
        int end = 0; // 本次跳跃能跳到最远的位置
        for (int i = 0; i < nums.length - 1; i++) {
            maxPos = Math.max(maxPos, i + nums[i]);
            if (end == i) {
                // 当跳到上次跳跃最远位置时，能确定本次跳跃的最远位置
                skipNum++;
                end = maxPos;
            }
        }
        return skipNum;
    }
}
