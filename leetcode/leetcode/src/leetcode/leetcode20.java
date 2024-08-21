package leetcode;

import java.util.Arrays;

public class leetcode20 {
    public static void main(String[] args) {
        System.out.println(maxArea(new int[1]));

        System.out.println();
        int[] nums1 = {1,2,3,0,0,0};
        int[] nums2 = new int[]{2,5,6};
        merge(nums1, 3, nums2, 3);

        rotate(nums1, 3);
    }

    /**
     * 11.盛最多水的容器
     * 给定一个长度为 n 的整数数组 height 。有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
     * 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
     * 返回容器可以储存的最大水量。
      */
    public static int maxArea(int[] height1) {
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        int left = 0;
        int right = height.length - 1;
        int max = 0;

        while (left < right) {

            if (height[left] < height[right-1]) {
                max = Math.max(max, (right - left) * height[left]);
                left++;
            } else {
                max = Math.max(max, (right - left) * height[right]);
                right--;
            }
        }
        return max;
    }

    /**
     * https://leetcode.cn/problems/merge-sorted-array/?envType=study-plan-v2&envId=top-interview-150
     * 88.合并两个有序数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {
        while(m > 0 && n > 0) {
            if(nums1[m-1] > nums2[n-1]) {
                nums1[m+n-1] = nums1[m-1];
                m--;
            } else {
                nums1[m+n-1] = nums2[n-1];
                n--;
            }
        }

        // num1 数组数据都已经后移完成了，需要将num2剩余数据全部移动到num1前面
        if(m == 0 && n>0) {
            for(int i = 0; i < n; i++) {
                nums1[i] = nums2[i];
            }
        }

        Arrays.stream(nums1).forEach(System.out::println);
    }

    /**
     * https://leetcode.cn/problems/remove-element/description/?envType=study-plan-v2&envId=top-interview-150
     * 27.移除元素
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if (nums.length == 0)
            return 0;

        int low = 0;
        int fast = 0;
        while (fast < nums.length) {
            if (nums[fast] != val) {
                nums[low] = nums[fast];
                low ++;
            }
            fast++;
        }
        return low;
    }

    /**
     * https://leetcode.cn/problems/remove-duplicates-from-sorted-array/?envType=study-plan-v2&envId=top-interview-150
     * 26. 删除有序数组中的重复项
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int fast = 0, slow = 0;
        while (fast < nums.length) {
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast];
            }
            fast++;
        }
        return slow + 1;
    }

    /**
     * https://leetcode.cn/problems/move-zeroes/
     * 283. 移动零
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int fast = 0, slow = 0;

        while (fast < nums.length) {
            if (nums[fast] != 0) {
                int temp = nums[fast];
                nums[fast] = nums[slow];
                nums[slow] = temp;
                slow++;
            }

            fast++;
        }
    }

    /**
     * https://leetcode.cn/problems/remove-duplicates-from-sorted-array-ii/description/?envType=study-plan-v2&envId=top-interview-150
     * 80. 删除有序数组中的重复项 II
     * 因为本题要求相同元素最多出现两次而非一次，所以我们需要检查上上个应该被保留的元素 nums[slow−2] 是否和当前待检查元素 nums[fast] 相同。
     * @param nums
     * @return
     */
    public int removeDuplicates2(int[] nums) {
        if (nums.length <= 2)
            return nums.length;

        int slow = 2, fast = 2;
        while (fast != nums.length) {
            if (nums[fast] != nums[slow-2]) {
                nums[slow] = nums[fast];
                slow++;
            }
            fast++;
        }
        return slow;
    }

    /**
     * https://leetcode.cn/problems/majority-element/?envType=study-plan-v2&envId=top-interview-150
     * 169. 多数元素
     * 摩尔算法，适合求众数
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        // Arrays.sort(nums);
        // return nums[nums.length / 2];
        int cand_num = nums[0], count = 1;

        for (int i = 1; i < nums.length; i++) {
            if (count == 0) {
                cand_num = nums[i];
            }

            if (cand_num == nums[i]) {
                count++;
            } else {
                count--;
            }
        }
        return cand_num;
    }

    /**
     * https://leetcode.cn/problems/rotate-array/?envType=study-plan-v2&envId=top-interview-150
     * 189. 轮转数组
     * 先整体反转，然后以 k%num.length 取余，两边分别反转
     * @param nums
     * @param k
     */
    public static void rotate(int[] nums, int k) {
        int len = k % nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, len-1);
        reverse(nums, len, nums.length - 1);
        Arrays.stream(nums).forEach(System.out::println);
    }

    public static void reverse(int[] nums, int start, int end) {
        while (end > start) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }


}
