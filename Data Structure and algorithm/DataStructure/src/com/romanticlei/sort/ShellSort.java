package com.romanticlei.sort;

import java.util.Arrays;

public class ShellSort {

    public static void main(String[] args) {
        // int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        // shellSort(arr);
        // System.out.println("排序后的数组为 " + Arrays.toString(arr));

        // 测试插入排序效率
        int[] array = new int[80000];
        for (int i = 0; i < 80000; i++) {
            array[i] = (int) (Math.random() * 80000);
        }

        long currentTimeMillis_start = System.currentTimeMillis();
        shellSort2(array);
        long currentTimeMillis_end = System.currentTimeMillis();
        // 希尔排序数据量大比较耗时 1375(时间与机器性能有关)
        System.out.println("一共耗时：" + (currentTimeMillis_end - currentTimeMillis_start));
        // System.out.println("排序后的数组为 " + Arrays.toString(array));
    }

    // 交换式希尔排序
    public static void shellSort(int[] arr) {
        int temp = 0;
        // gap 为将数组分为多少组，同时也是步长
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                for (int j = i - gap; j >= 0; j -= gap) {
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
        }
    }

    // 移动法希尔排序
    public static void shellSort2(int[] arr) {
        int temp = 0;
        // gap 为将数组分为多少组，同时也是步长
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                temp = arr[j];
                // 当前值比前一个步长的值小，那么就移动覆盖
                while (j - gap >= 0 && temp < arr[j - gap]){
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                // 将最小值放到本次排序前面
                arr[j] = temp;
            }
        }
    }
}
