package com.romanticlei.floyd;

import java.util.Arrays;

public class FloydAlgorithm {

    public static void main(String[] args) {

    }

}

// 创建图
class Graph {
    private char[] vertexs; // 存放顶点的数组
    private int[][] dis;    // 保存从各个顶点到其他顶点的距离
    private int[][] pre;    // 保存到达目标顶点的前驱结点

    /**
     *
     * @param length    创建数组的长度
     * @param matrix    邻接矩阵
     * @param vertexs   顶点数组
     */
    public Graph(int length, int[][] matrix, char[] vertexs) {
        this.vertexs = vertexs;
        this.dis = matrix;
        this.pre = new int[length][length];

        // 对 pre数组进行初始化，数组中存放的是前驱顶点的下标
        for (int i = 0; i < length; i++) {
            Arrays.fill(pre[i], i);
        }
    }

}
