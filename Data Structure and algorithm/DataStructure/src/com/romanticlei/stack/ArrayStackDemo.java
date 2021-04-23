package com.romanticlei.stack;

import java.util.Scanner;

public class ArrayStackDemo {

    public static void main(String[] args) {
        // 创建一个ArrayStack 对象
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("show：表示显示栈");
            System.out.println("exit：表示退出栈");
            System.out.println("push：表示数据入栈");
            System.out.println("pop：表示数据出栈");
            System.out.println("请输入您的选择");
            key = scanner.next();
            switch (key){
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数：");
                    int val = scanner.nextInt();
                    stack.push(val);
                    break;
                case "pop":
                    int value = stack.pop();
                    System.out.println("出栈的数据为：" + value);
                    break;
                case "exit":
                    System.exit(-1);
                    break;
                default:
                    System.out.println("输入有误！");
                    break;
            }
        }

    }
}

class ArrayStack {
    private int maxSize;    // 栈的大小
    private int[] stack;    // 数组，数组模拟栈，数据就放在该数组中
    private int top = -1;   // top 表示栈顶，初始化为-1

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        this.stack = new int[maxSize];
    }

    // 判断栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 判断栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 入栈
    public void push(int value) {
        // 判断是否栈满
        if (isFull()) {
            System.out.println("栈已满！");
            return;
        }

        top++;
        stack[top] = value;
    }

    // 出栈
    public int pop(){
        if (isEmpty()){
            System.out.println("栈已空");
            return -1;
        }

        int value = stack[top];
        top--;
        return value;
    }

    // 遍历栈，遍历时，需要从栈顶开始显示数据
    public void list(){
        if (isEmpty()){
            System.out.println("栈已空");
            return;
        }

        for (int i = top; i >= 0; i--) {
            System.out.println("出栈数据为stack[" + i + "] = " + stack[i]);
        }
    }

}
