package com.romanticlei.sort;

import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {
        int arr[] = {3, 9, -1, 10, -2};
        int temp;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }

        System.out.println("排序完毕之后的数组为 ： " + Arrays.toString(arr));
    }

}
