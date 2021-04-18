package com.romanticlei.queue;

public class ArrayQueue {

    public static void main(String[] args) {

    }

}

// 使用数组模拟队列-编写一个ArrayQueue类
class ArrayQueue{
    // 表示数组的最大容量
    private int maxSize;
    // 队列头
    private int front;
    // 队列尾
    private int rear;
    // 该数组用于存储数据，模拟队列
    private int[] arr;

    public ArrayQueue(int maxSize){
        this.maxSize = maxSize;
        arr = new int[maxSize];
        front = -1; // 指向队列头部；分析出front 是指向队列头的前一个位置
        rear = -1; // 指向队列尾，指向队列尾的数据（即时队列最后一个数据）
    }
}
