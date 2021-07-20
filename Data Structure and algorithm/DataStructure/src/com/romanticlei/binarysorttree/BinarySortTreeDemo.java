package com.romanticlei.binarysorttree;

public class BinarySortTreeDemo {
    public static void main(String[] args) {

    }
}

class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public void addNode(Node node){
        if (node == null) {
            return;
        }

        // 判断传入的节点，和当前子树结点的大小关系
        if (node.value < this.value) {
            if (this.left == null) {
                // 左子结点为空直接添加
                this.left = node;
            } else {
                // 递归向左子树添加新结点
                this.left.addNode(node);
            }
        } else {
            if (this.right == null) {
                this.right = node;
            } else {
                // 递归向右子节点添加新数据
                this.right.addNode(node);
            }
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }

        System.out.println(this);

        if (this.right != null) {
            this.right.infixOrder();
        }
    }

}
