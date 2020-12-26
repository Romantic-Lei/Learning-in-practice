package com.romanticlei.LRUCacheDemo;

import java.util.Map;

public class LRUCacheDemo1 {

    /**
     * 思路：
     * map 负责查找，构建一个虚拟的双向链表，它里面安装的就是一个个Node节点，作为数据载体
     * @param <K,  V>
     */

    // 1.构建一个Node节点，作为数据载体
    class Node<K,  V>{
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node() {
            this.prev = this.next = null;
        }

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.prev = this.next = null;
        }
    }

    // 2.构造一个虚拟的双向队列，里面安放的就是我们的Node
    class DoubleLinkedList<K, V>{
        Node<K, V> head;
        Node<K, V> tail;

        // 2.1 构造方法
        public DoubleLinkedList() {
            head = new Node<>();
            tail = new Node<>();
            head.next = tail;
            tail.next = head;
        }

        // 2.2 构造到头
        public void addHead(Node<K, V> node){
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        // 2.3 删除节点
        public void removeNode(Node<K, V> node){
            head.next.prev = node.prev;
            head.next = node.next;
            node.prev = null;
            node.next = null;
        }

        // 2.4 获得最后一个结点
        public Node getLast(){
            return tail.prev;
        }

    }

    private int cacheSize;
    Map<Integer, Integer> map;
    DoubleLinkedList<Integer, Integer> doubleLinkedList;

    public LRUCacheDemo1(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    // 2.构造一个双向队列，里面安放的就是我们的Node
    public static void main(String[] args) {

    }
}
