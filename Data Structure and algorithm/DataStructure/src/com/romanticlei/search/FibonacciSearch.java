package com.romanticlei.search;

import java.util.Arrays;

public class FibonacciSearch {

    public static int maxSize = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};

        System.out.println("index = " + fibSearch(arr, 89));
    }

    // 创建一个斐波那契数组
    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        return f;
    }

    /**
     * 编写一个斐波那契查找算法， 使用非递归的方式编写
     * @param a
     * @param key
     * @return
     */
    public static int fibSearch(int[] a, int key) {
        int low = 0;
        int high = a.length - 1;
        int k = 0; // 表示斐波那契分隔数值的下标
        int mid = 0; // 存放mid值
        int[] f = fib(); // 获取斐波那契数列
        // 获取到斐波那契分隔数的下标
        while (high > f[k] - 1) {
            k++;
        }

        // 因为f[k]值可能大于 数组a的长度，因此我们需要使用Arrays 类，构造一个新的数组
        // Arrays.copyOf(a, f[k]); 参数意思 =》 旧的数组，新的数组的长度
        int[] temp = Arrays.copyOf(a, f[k]);
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = a[high];
        }

        // 使用 while 来循环处理，找到我们的数 key
        while (low <= high) {
            mid = low + f[k - 1] - 1;
            if (key < temp[mid]) {
                high = mid - 1;
                k--; // 下次循环向数组的左边查找
            } else if (key > temp[mid]) {
                low = mid + 1;
                // 为什么是 k -= 2
                // 1. 全部元素 = 前面的元素 + 后面的元素
                // 2.f[k] = f[k-1] + f[k-2]
                // 3.因为后面我们有 f[k-2] 所以可以继续拆分 f[k-2] = f[k-3] +f[k-4]
                // 4.即在f[k-2] 的前面进行查找 k-=2
                // 5.即下次循环mid = f[k-1-2]-1
                k -= 2; // 下次循环向数组的右边查找
            } else {
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }

        return -1;
    }

}
