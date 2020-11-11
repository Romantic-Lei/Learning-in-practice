package com.romanticLei;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 *
 * @author Romantic-Lei
 * @version 1.0
 * @date 2020/11/11
 *
 */
public class TwoSumDemo {

    /**
     * 遍历----> 暴力破解。。。
     *
     * @param nums      集合数组
     * @param target    目标值
     * @return
     */
    public static int[] twoSum1(int[] nums, int target){
        for (int i = 0; i < nums.length; i++){
            for (int j = i + 1; j < nums.length; j++) {
                if (target - nums[i] == nums[j]){
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    /**
     * Hash破解
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++){
            int partnerNumber = target - nums[i];
            if (map.containsKey(partnerNumber)){
                return new int[]{map.get(partnerNumber), i};
            }
            map.put(nums[i], i);
        }

        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2,11,7,15};
        int target = 9;

        int[] myIndex = twoSum1(nums, target);

        if (myIndex != null){
            for (int element : myIndex) {
                System.out.println(element);
            }
        }else {
            System.out.println("未找到");
        }
    }

}
