package com.romanticlei.avl;

public class AVLTreeDemo {

    public static void main(String[] args) {

        // 右子树高
        // int[] arr = new int[]{4, 2, 6, 5, 7, 8};
        // 左子树高
        // int[] arr = new int[]{10, 12, 8, 9, 7, 6};
        // 双旋转
        int[] arr = new int[]{10, 11, 7, 6, 8, 9};

        AVLTree avlTree = new AVLTree();
        // 添加节点
        for (int i = 0; i < arr.length; i++) {
            avlTree.addNode(new Node(arr[i]));
        }

        System.out.println("中序遍历");
        // 2, 4, 5, 6, 7, 8
        avlTree.infixOrder();
        System.out.println("没有平衡之前二叉树的高度");
        // 树的高度 = 4
        System.out.println("树的高度 = " + avlTree.getRoot().height());
        // 树的左子树高度 = 2
        System.out.println("树的左子树高度 = " + avlTree.getRoot().leftHeight());
        // 树的左子树高度 = 2
        System.out.println("树的右子树高度 = " + avlTree.getRoot().rightHeight());
        System.out.println("根结点的值为" + avlTree.getRoot());


    }
}

class AVLTree {
    private Node root;

    public Node getRoot() {
        return root;
    }

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
                    // 是左子节点
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) {
                    // 是右子节点
                    parent.right = null;
                }
            } else if (targetNode.left != null && targetNode.right != null) {
                // 删除两颗子树的结点
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value = minVal;
            } else {
                // 删除只有一颗子树的结点
                // 如果要删除的结点有左子节点
                if (targetNode.left != null) {
                    // 如果 targetNode 是parent 的左子节点
                    if (parent.left != null && parent.left.value == value) {
                        parent.left = targetNode.left;
                    } else {
                        // 如果 targetNode 是parent 的右子节点
                        parent.right = targetNode.left;
                    }
                } else {
                    // 如果 targetNode 是parent 的左子节点
                    if (parent.left != null && parent.left.value == value) {
                        parent.left = targetNode.right;
                    } else {
                        // 如果 targetNode 是parent 的右子节点
                        parent.right = targetNode.right;
                    }
                }
            }
        }
    }

    /**
     * 删除以node为根节点的二叉排序树的右结点最小结点，并将其保存在一个temp临时变量中，
     * 然后删除，然后将node的值替换成temp的值，
     * 这样这颗二叉排序树的左子树所有值全比temp值小，右子树所有值比temp值大
     *
     * @param node 传入的结点
     * @return 返回以node为根节点的二叉排序树的最小结点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        while (target.left != null) {
            target = target.left;
        }

        // 找到 target 就指向了右子树的最小结点
        delNode(target.value);
        // 返回最小结点的值
        return target.value;
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

    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    // 返回当前结点的高度，以该结点为根结点的树的高度
    public int height() {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    // 左旋转方法
    public void leftRotate() {
        // 以当前根结点的值，创建新的结点
        Node newNode = new Node(value);
        // 把新的结点的左子树设置为当前结点(根结点)的左子树
        newNode.left = this.left;
        // 把新的结点的右子树设置为当前结点(根结点)的右子树的左子树
        newNode.right = this.right.left;
        // 把当前结点(根结点)的值替换成当前结点(根结点)的右子结点的值
        this.value = this.right.value;
        // 把当前结点(根结点)的右子树设置成当前结点右子树的右子树
        this.right = this.right.right;
        // 把当前结点(根结点)的左子树设置成新的结点
        this.left = newNode;
    }

    // 右旋转方法
    public void rightRotate() {
        // 以当前结点的值，创建新的结点
        Node newNode = new Node(this.value);
        // 把新结点的右子树设置成当前结点的右子树
        newNode.right = this.right;
        // 把当前结点左子树的右子树设置成为新结点的左子树
        newNode.left = this.left.right;
        // 把当前结点的左子结点值替换成为当前结点的值
        this.value = this.left.value;
        // 把当前结点的左子树设置成为当前结点的左子树的左子树
        this.left = this.left.left;
        // 把当前结点的右子树设置成新节点
        this.right = newNode;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    public void addNode(Node node) {
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

        // 当添加完一个结点后，如果(右子树高度 - 左子树高度) > 1， 左旋
        if (rightHeight() - leftHeight() > 1) {
            // 如果根结点的左子树大于根结点大于根结点的右子树
            if (this.right != null && this.right.leftHeight() > this.right.rightHeight()) {
                // 对当前结点进行右旋
                this.right.rightRotate();
            }
            // 对整体进行左旋
            leftRotate();

            // 左旋之后不需要在去后面进行右旋判断，直接返回
            return;
        }

        // 当添加完一个结点后，如果(左子树高度 - 右子树高度) > 1， 右旋
        if (leftHeight() - rightHeight() > 1) {
            // 如果根结点的左子树右子树高度大于根结点的左子树左子树
            if (this.left != null && this.left.rightHeight() > this.left.leftHeight()) {
                // 对当前结点的左子树进行左旋
                this.left.leftRotate();
            }
            // 将整体进行右旋
            rightRotate();
        }

    }

    /**
     * 查找结点
     *
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
     *
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
