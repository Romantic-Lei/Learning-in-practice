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
    public void preOrder(){
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
