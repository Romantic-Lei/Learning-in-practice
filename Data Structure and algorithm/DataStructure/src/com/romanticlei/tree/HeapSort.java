package com.romanticlei.tree;

import java.util.Arrays;

public class HeapSort {
    public static void main(String[] args) {
        // 要求将数组进行升序排列
        int arr[] = {4, 6, 8, 5, 9, -1, 0, -9, 7};
        heapSort(arr);

        // 测试插入排序效率
        int[] array = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            array[i] = (int) (Math.random() * 8000000);
        }

        long currentTimeMillis_start = System.currentTimeMillis();
        heapSort(array);
        long currentTimeMillis_end = System.currentTimeMillis();
        // 堆排序数据量大排序很快 2665(八百万数据，时间与机器性能有关)
        System.out.println("一共耗时：" + (currentTimeMillis_end - currentTimeMillis_start));

    }

    public static void heapSort(int arr[]) {
        int temp = 0;
        System.out.println("堆排序！");

        // adjustHeap(arr, 1, arr.length);
        // // 第一次排序[4, 9, 8, 5, 6]
        // System.out.println("第一次排序" + Arrays.toString(arr));
        // adjustHeap(arr, 0, arr.length);
        // // 第一次排序[9, 6, 8, 5, 4]
        // System.out.println("第二次排序" + Arrays.toString(arr));

        // 将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        // 将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端;
        for (int i = arr.length - 1; i >= 0; i--) {
            // 交换
            temp = arr[i];
            arr[i] = arr[0];
            arr[0] = temp;
            // 已经是大顶堆了，只是根节点和最后一个叶子结点交换
            // 所以我们只需要从第一个节点开始向下整理排序
            adjustHeap(arr, 0, i);
        }

        // System.out.println(Arrays.toString(arr));
    }

    /**
     * 将一个数组（二叉树），调整成一个大顶堆
     * @param arr   待调整的数组
     * @param i     表示非叶子结点在数组中索引
     * @param length 表示对多少个元素继续调整，length 是在逐渐的减少
     */
    public static void adjustHeap(int arr[], int i, int length){
        // 先取出当前元素的值，保存在哎临时变量中
        int temp = arr[i];
        // 开始调整， k = i * 2 + 1 k是i的左子节点
        for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {
            // 判断左右子节点的大小
            if (k+1 < length && arr[k] <arr[k + 1]) {
                k++;
            }

            // 父节点与子节点比较大小
            if (arr[k] > temp) {
                arr[i] = arr[k];
                i = k;
            } else {
                // 因为是在最下面开始查找的，所以树的下面是有序的，
                // 一旦上面符合大顶堆，就不需要继续向下比较
                break;
            }
        }

        // 当for 循环结束，我们已经将以i 为父节点的数的最大值，放在了局部最顶端
        // 将原来的值放到新替换的位置
        arr[i] = temp;
    }
}
