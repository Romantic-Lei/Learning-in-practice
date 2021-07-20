package com.romanticlei.binarysorttree;

public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.addNode(new Node(arr[i]));
        }

        System.out.println("中序遍历二叉排序树~~~");
        // 1, 2, 3, 5, 7, 9, 10, 12
        binarySortTree.infixOrder();

        System.out.println("删除结点后的二叉排序树~~~");
        binarySortTree.delNode(2);
        binarySortTree.infixOrder();
    }
}

class BinarySortTree {
    private Node root;
    // 添加节点的方法
    public void addNode(Node node) {
        if (root == null) {
            // 如果root 为空，则直接让root指向node(即第一个结点进来的时候初始化)
            root = node;
        } else {
            root.addNode(node);
        }
    }

    // 查找结点
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    // 查找父结点
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    public void delNode(int value) {
        if (root == null) {
            return;
        } else {
            // 需求先去找到要删除的结点
            Node targetNode = search(value);
            // 如果没有找到要删除的结点，直接返回
            if (targetNode == null) {
                return;
            }

            // 能走下来说明找到了需要删除的结点
            // 如果我们发现当前这颗二叉树只有一个结点，则直接删除
            if (root.left == null && root.right == null) {
                root = null;
                return;
            }
            
            // 去找 targetNode 的父结点
            Node parent = searchParent(value);
            // 处理当前需要删除的结点是叶子结点的情况
            if (targetNode.left == null && targetNode.right == null) {
                // 判断 targetNode 是父节点的左子节点还是右子节点
                if (parent.left != null && parent.left.value == value) {
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) {
                    parent.right = null;
                }
            }
        }
    }

    // 中序遍历
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("遍历结点无数据！");
        }
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

    /**
     * 查找结点
     * @param value 需要查找结点的值
     * @return
     */
    public Node search(int value) {
        if (value == this.value) {
            // 结点找到，返回
            return this;
        } else if (value < this.value) {
            if (this.left == null) {
                // 如果无左子结点，返回 null
                return null;
            }
            // 向左递归查找
            return this.left.search(value);
        } else {
            if (this.right == null) {
                return null;
            }
            return this.right.search(value);
        }
    }

    /**
     * 查找要删除结点的父节点
     * @param value
     * @return
     */
    public Node searchParent(int value) {
        // 如果当前结点就是要删除的结点，就返回
        if ((this.left != null && this.left.value == value)
                || (this.right != null && this.right.value == value)) {
            return this;
        }

        // 如果查找的值小于当前结点的值，并且当前结点的左子节点不为空
        if (value < this.value && this.left != null) {
            // 如果查找的值小于当前结点的值，并且当前结点的左子结点不为空，递归
            return this.left.searchParent(value);
        } else if (value >= this.value && this.right != null) {
            return this.right.searchParent(value);
        } else {
            // 没有找到符合条件的数据，返回null
            return null;
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
