package com.romanticlei.sort;

public class RadixSort {

    public static void main(String[] args) {
        int[] arr = {53, 3, 542, 748, 14, 214};
    }

    // 基数排序方法
    public static void radixSort(int[] arr) {

        // 找到数组中最大数的位数
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]){
                max = arr[i];
            }
        }

        // 定义一个变量获取最大值的长度
        int maxLength = (max + "").length();

        // 定义一个二维数组，表示十个桶，每个桶就是一个一维数组
        // 说明：二维数组包含十个桶
        // 为了防止放入数据时，数据溢出，则每个一维数组（桶）的大小定位arr.length
        // 所以很明确，基数排序就是使用的空间换时间的算法
        int[][] bucket = new int[10][arr.length];

        // 为了记录每个桶中，实际有多少数据，我们定义一个一维数组来记录各个桶的每次放入的数据个数
        // 比如 bucketElementCounts[0] 记录的就是 bucket[0] 桶的放入数据个数
        int[] bucketElementCounts = new int[10];

        for (int i = 0; i < maxLength; i++) {
            for (int j = 0; j < arr.length; j++) {
                // 获取每个元素的某位的值
            }
        }


    }
}
