package com.romanticlei.recursion;

public class Queue8 {

    // 定义一个 max 表示共有多少个皇后
    int max = 8;
    // 定义数组array， 保存皇后放置的结果，比如 arr = {0, 4, 7, 5, 3, 6, 1, 3}
    int[] array = new int[max];

    // 声明一个变量，表示有多少种解法
    int count = 0;
    // 声明一个变量，表示回溯了多少次
    int judgeCount = 0;

    public static void main(String[] args) {
        Queue8 queue = new Queue8();
        queue.check(0);
        System.out.println("八皇后问题一共有 " + queue.count + " 种解法");
        System.out.println("八皇后问题一共回溯了 " + queue.judgeCount + " 次");
    }

    // 编写一个方法，放置第n个皇后
    public void check(int n){
        if (n >= max){
            print();
            return;
        }

        // 依次放入皇后，并判断是否冲突
        for (int i = 0; i < max; i++) {
            judgeCount++;
            // 先把当前这个皇后 n，放到该行的第一列
            array[n] = i;
            // 判断当前放置第 n 个皇后到i列时，是否冲突
            if (judge(n)){
                // 不冲突接着放 n + 1个皇后，开始递归
                // 发生冲突就继续执行for循环
                check(n + 1);
            }
        }
    }

    // 查看当我们放置第n 个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突
    private boolean judge(int n) {
        for (int i = 0; i < n; i++) {
            // 如果当前皇后和前面的皇后在一条直线 或者 当前皇后与前面的皇后之间斜率绝对值为1 ,返回false
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[i] - array[n])) {
                return false;
            }
        }
        return true;
    }

    // 输出方法，用于将皇后摆放的位置输出
    private void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
        count++;
        System.out.println();
    }
}
