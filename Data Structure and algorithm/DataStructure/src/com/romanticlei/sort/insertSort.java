package com.romanticlei.sort;

import java.util.Arrays;

public class insertSort {

    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1, -1, 68};
        insertSort(arr);
    }
    
    public static void insertSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            int insertValue = arr[i];
            int insertIndex = i - 1;
            while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            arr[insertIndex + 1] = insertValue;
            System.out.println("第" + i + "次排序后的结果是：" + Arrays.toString(arr));
        }
    }
}
