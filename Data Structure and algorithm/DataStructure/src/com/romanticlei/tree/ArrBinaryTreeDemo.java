package com.romanticlei.tree;

public class ArrBinaryTreeDemo {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        // 创建一个 ArrBinaryTree
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        // 1 2 4 5 3 6 7
        arrBinaryTree.preOrder(0);
    }
}

// 编写一个ArrayBinaryTree，实现顺序存储二叉树
class ArrBinaryTree {
    // 存储数据节点的数组
    private int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    /**
     * 编写一个方法，完成顺序存储二叉树的前序遍历
     * @param index
     */
    public void preOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }

        // 输出当前这个元素
        System.out.println(arr[index]);
        // 向左递归
        if ((2 * index + 1) < arr.length){
            this.preOrder(2 * index + 1);
        }

        if ((2 * index + 2) < arr.length){
            this.preOrder(2 * index + 2);
        }

    }
}
