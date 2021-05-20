package com.romanticlei.tree;

public class HeapSort {
    public static void main(String[] args) {
        // 要求将数组进行升序排列
        int arr[] = {4, 6, 8, 5, 9};
    }

    public static void heapSort(int arr[]) {
        System.out.println("堆排序！");
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
