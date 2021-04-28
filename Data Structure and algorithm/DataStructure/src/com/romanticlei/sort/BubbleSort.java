package com.romanticlei.sort;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {
        int arr[] = {3, 9, -1, 10, 20};
        System.out.println("排序前");
        System.out.println(Arrays.toString(arr));

        bubbleSort(arr);
        System.out.println("排序后");
        System.out.println(Arrays.toString(arr));

        // 测试冒泡排序的速度
        int[] array = new int[80000];
        for (int i = 0; i < 80000; i++) {
            array[i] = (int)(Math.random() * 80000);
        }

        long currentTimeMillis_start = System.currentTimeMillis();
        bubbleSort(array);
        long currentTimeMillis_end = System.currentTimeMillis();
        // 冒泡排序数据量大比较耗时 15067(时间与机器性能有关)
        System.out.println("一共耗时：" + (currentTimeMillis_end - currentTimeMillis_start));
    }

    public static void bubbleSort(int arr[]){
        // 定义一个标识，表示是否进行过交换
        boolean flag = false;
        int temp;
        // 冒泡排序时间复杂度O(n^2)
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 有过交换就将标识置为true
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            if (!flag) {
                // 在某次排序中一次交换都没有发生，表示已经是有序的了
                break;
            }
            // 将标识重置，进行下次判断
            flag = false;
        }
    }

}
