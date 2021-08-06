package com.romanticlei.dac;

public class Hanoitower {

    public static void main(String[] args) {
        hanoiTower(5, 'A', 'B', 'C');
    }

    /**
     * 汉诺塔的移动方法，使用分治算法
     * @param num
     * @param a 起始位置
     * @param b 辅助位置
     * @param c 目标位置
     */
    public static void hanoiTower(int num, char a, char b, char c) {
        // 如果只有一个盘
        if (num == 1) {
            System.out.println("第一个盘从" + a + "->" + c);
        } else {
            // 如果我们有 n >= 2 的情况，我们总是可以看做是两个盘。1.最下面的一个盘；2.上面的所有盘
            // 1. 先把最上面的所有盘 A -> B, 移动过程中使用到C
            hanoiTower(num - 1, a, c, b);
            // 2.把最下边的盘 A -> C
            System.out.println("第" + num + "个盘从" + a + "->" + c);
            // 3. 把B塔的所有盘从 B -> C，移动过程中使用到 A塔
            hanoiTower(num - 1, b, a, c);
        }
    }
}
