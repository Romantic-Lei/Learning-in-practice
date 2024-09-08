package leetcode.leetcode150.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum15 {

    /**
     * https://leetcode.cn/problems/3sum/description/?envType=study-plan-v2&envId=top-interview-150
     *
     * 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]] 满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。请你返回所有和为 0 且不重复的三元组。
     * 注意：答案中不可以包含重复的三元组。
     *
     * https://leetcode.cn/problems/3sum/?envType=study-plan-v2&envId=top-interview-150
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; i++) {
            // 因为排序，所以当前位置左边的不用再计算，左边要么没有答案，要么答案已经列出来了
            if (i > 0 && nums[i] == nums[i-1]) {
                // 和左边一样就不用处理
                continue;
            }

            int left = i + 1;
            int right = n - 1;
            int target = nums[i];

            while (left < right) {
                // left > i + 1 的目的是需要跳过当前值和目标target的比较
                // 内层循环为 i + 1 时就能找到答案，即时第三个数在i+2的位置，因为第一次的时候就跳过了这里的比较，到下面找真正的答案了
                if(left > i + 1 && nums[left] == nums[left - 1]) {
                    left++;
                    continue;
                }

                if (nums[left] + nums[right] + target < 0) {
                    left++;
                } else if (nums[left] + nums[right] + target > 0) {
                    right--;
                } else {
                    List<Integer> list = new ArrayList();
                    list.add(nums[i]);
                    list.add(nums[left]);
                    list.add(nums[right]);
                    ans.add(list);
                    left++;
                    right--;
                    // 不能跳出去，因为left右边可能还有答案，left需要继续右移，right左移
                    // 例如拿 -1,0,1,2,-1,-4 数组分析
                    // break;
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{-2,0,3,-1,4,0,3,4,1,1,1,-3,-5,4,0};
        // int[] nums = new int[]{-1,0,1,2,-1,-4};
        List<List<Integer>> lists = threeSum(nums);
        System.out.println(lists);

    }

}
// [[-5,1,4],[-3,-1,4],[-3,0,3],[-2,-1,3],[-2,1,1],[-1,0,1],[0,0,0]]
// [[-5, 1, 4], [-3, -1, 4], [-3, 0, 3], [-2, -1, 3], [-2, 1, 1], [-1, 0, 1]]