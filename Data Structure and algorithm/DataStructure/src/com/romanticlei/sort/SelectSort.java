package com.romanticlei.sort;

import java.util.Arrays;

public class SelectSort {

    public static void main(String[] args) {
        int[] arr = {101, 34, 119, 1};
        System.out.println("排序前" + Arrays.toString(arr));
        selectSort(arr);
        System.out.println("排序后" + Arrays.toString(arr));

        // 测试插入排序效率
        int[] array = new int[80000];
        for (int i = 0; i < 80000; i++) {
            array[i] = (int)(Math.random() * 80000);
        }

        long currentTimeMillis_start = System.currentTimeMillis();
        selectSort(array);
        long currentTimeMillis_end = System.currentTimeMillis();
        // 冒泡排序数据量大比较耗时 4161(时间与机器性能有关)
        System.out.println("一共耗时：" + (currentTimeMillis_end - currentTimeMillis_start));
    }

    public static void selectSort(int[] arr){
        // 保存循环中最小的值
        int min = 0;
        int index = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            min = arr[i];
            index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    // 将当前值置为最小值
                    min = arr[j];
                    index = j;
                }
            }
            if (i != index) {
                arr[index] = arr[i];
                arr[i] = min;
            }
            // System.out.println("第" + (i + 1) + "次排序结果： " + Arrays.toString(arr));
        }
    }
}
