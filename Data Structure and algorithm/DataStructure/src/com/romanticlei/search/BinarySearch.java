package com.romanticlei.search;

import java.util.ArrayList;
import java.util.List;

public class BinarySearch {

    // 注意：使用二分查找的前提是 该数组是有序的
    public static void main(String[] args) {
        int arr[] = {1, 8, 10, 89, 1000, 1234};
        int index = binarySearch(arr, 0, arr.length - 1, 1000);
        System.out.println("resIndex = " + index);

        int arr2[] = {1, 8, 10, 89, 1000, 1000, 1000, 1234};
        List<Integer> integerList = binarySearchMore(arr2, 0, arr2.length - 1, 1000);
        if (integerList.size() == 0) {
            System.out.println("没有找到相对应的数据");
        } else {
            System.out.println("数据下标为: " + integerList);
        }
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

    /**
     * 二分查找算法,找到多个相同值
     * @param arr
     * @param left
     * @param right
     * @param findVal
     * @return
     */
    public static List<Integer> binarySearchMore(int[] arr, int left, int right, int findVal) {
        // 当left > right 时，说明递归了整个数组，但是没有找到
        if (left > right) {
            return new ArrayList<>();
        }

        int mid = (left + right) / 2;
        int midVal = arr[mid];

        if (midVal > findVal) {
            return binarySearchMore(arr, left, mid - 1, findVal);
        }

        if (midVal < findVal) {
            return binarySearchMore(arr, mid + 1, right, findVal);
        }

        List<Integer> resIndex = new ArrayList<>();
        // 向 mid 索引左边扫描，将所有满足条件的数据的下标，加入到集合中
        int temp = mid - 1;
        while (true) {
            if (temp < 0 || arr[temp] < findVal) {
                break;
            }
            resIndex.add(temp);
            temp--;
        }

        resIndex.add(mid);

        // 向 mid 索引右边扫描，将所有满足条件的数据的下标，加入到集合中
        temp = mid + 1;
        while (true) {
            if (temp > right || arr[temp] > findVal) {
                break;
            }
            resIndex.add(temp);
            temp++;
        }
        return resIndex;
    }
}
