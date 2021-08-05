package com.romanticlei.binarysearchnorecursion;

public class Binarysearchnorecursion {

    public static void main(String[] args) {
        //测试
        int[] arr = {1,3, 8, 10, 11, 67, 100};
        int index = binarySearch(arr, 100);
        System.out.println("index=" + index);
    }

    /**
     * 二分查找的非递归实现
     * @param arr 待查找的数组，arr 是升序排列或降序排列
     * @param target 需要查找的数
     * @return 返回对应的下标，-1表示没有找到
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            }

            if (arr[mid] > target) {
                right = mid - 1; // 需要向左查找
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }
}
