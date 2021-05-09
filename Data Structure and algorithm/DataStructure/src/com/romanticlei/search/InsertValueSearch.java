package com.romanticlei.search;

public class InsertValueSearch {

    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }

        int i = insertValueSearch(arr, 0, arr.length - 1, -1);
        System.out.println(i);
    }

    public static int insertValueSearch(int[] arr, int left, int right, int findVal){

        // 不加 findVal < arr[0] || findVal > arr[right] 在求mid时可能会出现数组越界
        if (left > right || findVal < arr[0] || findVal > arr[right]) {
            return -1;
        }

        // 求出mid
        int mid = left + (right - left) * (findVal - arr[left]) / (arr[right] - findVal);
        int midVal = arr[mid];

        if (midVal > findVal) {
            return insertValueSearch(arr, left, mid - 1, findVal);
        }

        if (midVal < findVal) {
            return insertValueSearch(arr, mid + 1, right, findVal);
        }

        return mid;
    }
}
