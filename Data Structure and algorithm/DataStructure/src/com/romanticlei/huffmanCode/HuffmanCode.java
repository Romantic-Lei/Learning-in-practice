package com.romanticlei.huffmanCode;

import java.util.*;

public class HuffmanCode {

    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        // 将对应的字母转成 ASCII
        byte[] contentBytes = content.getBytes();
        System.out.println(content.length()); // 40

        List<Node> nodes = getNodes(contentBytes);
        System.out.println("nodes = " + nodes);

        Node root = createHuffmanTree(nodes);
        // 赫夫曼树的根节点是： Node{data=null, weight=40}
        System.out.println("赫夫曼树的根节点是： " + root);
        preOrder(root);

        System.out.println("测试是否生成了对应的赫夫曼编码");
        getCodes(root, "", stringBuffer);
        // 生成的赫夫曼编码表 {32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
        System.out.println("生成的赫夫曼编码表 " + huffmanCodes);
    }

    // 前序遍历
    public static void preOrder(Node root) {
        if (root == null) {
            System.out.println("赫夫曼树为空！");
        } else {
            root.preOrder();
        }
    }

    // 将赫夫曼编码存放在 Map<Byte, String> 形式
    static Map<Byte, String> huffmanCodes = new HashMap<Byte, String>();
    // 在生成赫夫曼编码表示，需要拼接路径，定义一个StringBuilder 存储某个叶子结点的路径
    static StringBuffer stringBuffer = new StringBuffer();
    /**
     * 功能：将传入的Node 节点的所有叶子结点的赫夫曼编码得到，并放入的到集合中
     *
     * @param node         传入节点
     * @param code         路径：左子节点是0， 右子节点是1
     * @param stringBuffer 用于拼接路径
     */
    public static void getCodes(Node node, String code, StringBuffer stringBuffer) {
        StringBuffer stringBuffer1 = new StringBuffer(stringBuffer);
        stringBuffer1.append(code);
        if (node != null) {
            // 如node 为空表示这是非叶子节点
            if (node.data == null) {
                // 递归处理
                // 向左递归
                getCodes(node.left, "0", stringBuffer1);
                // 向右递归
                getCodes(node.right, "1", stringBuffer1);
            } else {
                // 说明是叶子结点
                huffmanCodes.put(node.data, stringBuffer1.toString());
            }
        }
    }

    private static List<Node> getNodes(byte[] bytes) {
        List<Node> nodes = new ArrayList<>();

        // 遍历 byte， 统计每一个 byte出现的次数 -> map[key, value]
        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        // 把每个键值对转成一个 Node 对象，并加入到nodes 集合
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }

        return nodes;
    }

    // 可以通过 List 创建对应的 赫夫曼树
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            // 取出第一颗最小的二叉树节点
            Node leftNode = nodes.get(0);
            // 取出第二颗最小的二叉树节点
            Node rightNode = nodes.get(1);
            // 父节点的数据域都是 null
            Node parent = new Node(null, leftNode.weight + rightNode.weight);

            parent.left = leftNode;
            parent.right = rightNode;

            // 将已处理的两颗二叉树从 nodes 删除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            // 将新的节点放入到集合中
            nodes.add(parent);
        }

        // 返回最后一个结点，这个结点就是赫夫曼树的根节点
        return nodes.get(0);
    }
}

// 创建一个Node，带数据和权值
class Node implements Comparable<Node> {

    Byte data; // 存放数据（字符）本身，比如 'a' = 97  ' ' = 32
    int weight;// 权重
    Node left; // 左子树
    Node right;// 右子树

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    // 前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }

        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}
