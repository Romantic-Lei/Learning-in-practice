package com.romanticlei.sort;

import java.util.Arrays;

public class insertSort {

    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1, -1, 68};
        insertSort(arr);

        // 测试插入排序效率
        int[] array = new int[80000];
        for (int i = 0; i < 80000; i++) {
            array[i] = (int)(Math.random() * 80000);
        }

        long currentTimeMillis_start = System.currentTimeMillis();
        insertSort(array);
        long currentTimeMillis_end = System.currentTimeMillis();
        // 冒泡排序数据量大比较耗时 1375(时间与机器性能有关)
        System.out.println("一共耗时：" + (currentTimeMillis_end - currentTimeMillis_start));
    }
    
    public static void insertSort(int[] arr){
        int insertValue = 0;
        int insertIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            insertValue = arr[i];
            insertIndex = i - 1;
            while (insertIndex >= 0 && insertValue < arr[insertIndex]) {
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }

            arr[insertIndex + 1] = insertValue;
            // System.out.println("第" + i + "次排序后的结果是：" + Arrays.toString(arr));
        }
    }
}
