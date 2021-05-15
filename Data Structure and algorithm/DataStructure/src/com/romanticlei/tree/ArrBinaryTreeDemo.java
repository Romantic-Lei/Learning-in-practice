package com.romanticlei.tree;

public class ArrBinaryTreeDemo {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        // 创建一个 ArrBinaryTree
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        // 1 2 4 5 3 6 7
        arrBinaryTree.preOrder(0);
        System.out.println();

        // 4 2 5 1 6 3 7
        arrBinaryTree.infixOrder(0);
        System.out.println();

        // 4 5 2 6 7 3 1
        arrBinaryTree.postOrder(0);
        System.out.println();


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
        System.out.print(arr[index] + "\t");
        // 向左递归
        if ((2 * index + 1) < arr.length){
            this.preOrder(2 * index + 1);
        }

        if ((2 * index + 2) < arr.length){
            this.preOrder(2 * index + 2);
        }
    }

    /**
     * 编写一个方法，完成顺序存储二叉树的中序遍历
     * @param index
     */
    public void infixOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }

        // 向左递归
        if ((2 * index + 1) < arr.length){
            this.infixOrder(2 * index + 1);
        }

        // 输出当前这个元素
        System.out.print(arr[index] + "\t");

        if ((2 * index + 2) < arr.length){
            this.infixOrder(2 * index + 2);
        }
    }

    /**
     * 编写一个方法，完成顺序存储二叉树的后序遍历
     * @param index
     */
    public void postOrder(int index) {
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }

        // 向左递归
        if ((2 * index + 1) < arr.length){
            this.postOrder(2 * index + 1);
        }

        if ((2 * index + 2) < arr.length){
            this.postOrder(2 * index + 2);
        }

        // 输出当前这个元素
        System.out.print(arr[index] + "\t");
    }
}
