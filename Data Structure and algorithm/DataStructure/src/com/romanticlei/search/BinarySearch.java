package com.romanticlei.search;

public class BinarySearch {

    // 注意：使用二分查找的前提是 该数组是有序的
    public static void main(String[] args) {
        int arr[] = {1, 8, 10, 89, 1000, 1234};
        int index = binarySearch(arr, 0, arr.length - 1, 1000);
        System.out.println("resIndex = " + index);
    }

    /**
     * 二分查找算法
     * @param arr
     * @param left
     * @param right
     * @param findVal
     * @return
     */
    public static int binarySearch(int[] arr, int left, int right, int findVal) {
        // 当left > right 时，说明递归了整个数组，但是没有找到
        if (left > right) {
            return -1;
        }

        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (midVal > findVal) {
            return binarySearch(arr, left, mid - 1, findVal);
        }

        if (midVal < findVal) {
            return binarySearch(arr, mid + 1, right, findVal);
        }

        return mid;
    }
}
