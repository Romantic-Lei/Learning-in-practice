package com.romanticlei.LRUCacheDemo;

import java.util.Collection;
import java.util.HashMap;
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

        // 2.2 添加到头
        public void addHead(Node<K, V> node){
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        // 2.3 删除节点
        public void removeNode(Node<K, V> node){
            // head.next.prev = node.prev;
            // head.next = node.next;
            // node.prev = null;
            // node.next = null;

            // tail.prev = node.prev;
            // node.prev.next = tail;
            // node.prev = null;
            // node.next = null;

            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.next = null;
            node.prev = null;
        }

        // 2.4 获得最后一个结点
        public Node getLast(){
            return tail.prev;
        }

    }

    private int cacheSize;
    Map<Integer, Node<Integer, Integer>> map;
    DoubleLinkedList<Integer, Integer> doubleLinkedList;

    public LRUCacheDemo1(int cacheSize) {
        this.cacheSize = cacheSize; // 坑位
        map = new HashMap<>(); // 查找
        doubleLinkedList = new DoubleLinkedList<>();
    }

    public int get(int key){
        // 获取不到对应的key，返回-1
        if (!map.containsKey(key)){
            return -1;
        }

        // 获取到对应节点之后，删除节点，然后添加到队头，最近最多使用的位置
        Node<Integer, Integer> node = map.get(key);
        doubleLinkedList.removeNode(node);
        doubleLinkedList.addHead(node);

        return node.value;
    }

    public void put(int key, int value){
        // 表中存在此key，先删除，后添加到队头
        if (map.containsKey(key)){
            Node<Integer, Integer> node = map.get(key);
            node.value = value;
            map.put(key, node);

            doubleLinkedList.removeNode(node);
            doubleLinkedList.addHead(node);
        }else {
            // 没有找到就去添加，添加新元素时需要判断是否超过坑位大小
            if (map.size() >= cacheSize){
                Node<Integer, Integer> lastNode = doubleLinkedList.getLast();
                map.remove(lastNode.key);
                doubleLinkedList.removeNode(lastNode);
            }

            // 将新节点新增进来
            Node<Integer, Integer> newNode = new Node<>(key, value);
            map.put(key, newNode);
            doubleLinkedList.addHead(newNode);
        }
    }

    // 2.构造一个双向队列，里面安放的就是我们的Node
    public static void main(String[] args) {
        LRUCacheDemo1 lruCacheDemo = new LRUCacheDemo1(3);

        lruCacheDemo.put(1, 1);
        lruCacheDemo.put(2, 2);
        lruCacheDemo.put(3, 3);
        System.out.println(lruCacheDemo.map.keySet());

        lruCacheDemo.put(5, 5);
        System.out.println(lruCacheDemo.map.keySet());
        lruCacheDemo.put(6, 6);
        System.out.println(lruCacheDemo.map.keySet());
        lruCacheDemo.put(3, 3);
        System.out.println(lruCacheDemo.map.keySet());
        lruCacheDemo.put(3, 3);
        System.out.println(lruCacheDemo.map.keySet());
        lruCacheDemo.put(31, 31);
        System.out.println(lruCacheDemo.map.keySet());

        for (Node<Integer, Integer> node : lruCacheDemo.map.values()){
            System.out.println(node.value);
        }

    }
}
