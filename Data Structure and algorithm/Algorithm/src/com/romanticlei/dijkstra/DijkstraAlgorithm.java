package com.romanticlei.dijkstra;

import java.util.Arrays;

public class DijkstraAlgorithm {

    public static void main(String[] args) {
        char[] vertexs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        // 邻接矩阵
        int[][] matrix = new int[vertexs.length][vertexs.length];
        final int N = 65535;// 表示不可以连接
        matrix[0]=new int[]{N,5,7,N,N,N,2};
        matrix[1]=new int[]{5,N,N,9,N,N,3};
        matrix[2]=new int[]{7,N,N,N,8,N,N};
        matrix[3]=new int[]{N,9,N,N,N,4,N};
        matrix[4]=new int[]{N,N,8,N,N,5,4};
        matrix[5]=new int[]{N,N,N,4,5,N,6};
        matrix[6]=new int[]{2,3,N,N,4,6,N};
        // 创建一个 Graph 对象
        Graph graph = new Graph(vertexs, matrix);
        //测试, 看看图的邻接矩阵是否 ok
        graph.showGraph();
    }
}

class Graph {
    private char[] vertexs; // 顶点数组
    private int[][] matrix; // 邻接矩阵

    public Graph(char[] vertexs, int[][] matrix) {
        this.vertexs = vertexs;
        this.matrix = matrix;
    }

    // 显示图
    public void showGraph() {
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }
}

// 已访问顶点集合
class VisitedVertex {
    // 记录各个顶点是否访问过 1 表示访问过,0 未访问,会动态更新
    public int[] already_arr;
    // 每个下标对应的值为前一个顶点下标, 会动态更新
    public int[] pre_visited;
    // 记录出发顶点到其他所有顶点的距离,比如 G 为出发顶点，就会记录 G 到其它顶点的距离，会动态更新，求 的最短距离就会存放到 dis
    public int[] dis;

    /**
     * 构造器
     * @param length 顶点的个数
     * @param index  出发顶点对应的下标，比如出发顶点为 G点，对应的下标为6
     */
    public VisitedVertex(int length, int index) {
        this.already_arr = new int[length];
        this.pre_visited = new int[length];
        this.dis = new int[length];
        // 初始化 dis 数组,默认全部都不可到达
        Arrays.fill(dis, 65535);
        // 设置出发顶点的访问距离为 0
        this.dis[index] = 0;
    }

    /**
     * 判断当前下标对应的顶点是否被访问过
     * @param index 当前顶点的下标
     * @return
     */
    public boolean isVisted(int index) {
        return already_arr[index] == 1;
    }

    /**
     * 更新出发顶点到 index下标对应顶点的距离
     * @param index
     * @param len
     */
    public void updateDis(int index, int len) {
        dis[index] = len;
    }
}
