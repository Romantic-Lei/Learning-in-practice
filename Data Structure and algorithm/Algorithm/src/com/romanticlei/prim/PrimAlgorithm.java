package com.romanticlei.prim;

import java.util.Arrays;

public class PrimAlgorithm {

    private static final int INF = 10000;
    public static void main(String[] args) {
        //测试看看图是否创建 ok
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;
        //邻接矩阵的关系使用二维数组表示,10000 这个大数，表示两个点不联通
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000}};


        // int weight[][] = {
        //         /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
        //         /*A*/ { 0,   12, INF, INF, INF, 16,  14},
        //         /*B*/ { 12,  0,   10, INF, INF,  7, INF},
        //         /*C*/ { INF, 10,   0,   3,   5,  6, INF},
        //         /*D*/ { INF, INF,  3,   0,   4,INF, INF},
        //         /*E*/ { INF, INF,  5,   4,   0,  2,   8},
        //         /*F*/ { 16,    7,  6, INF,   2,  0,   9},
        //         /*G*/ { 14, INF,  INF, INF,  8,  9,   0}};

        //创建 MGraph 对象
        MGraph graph = new MGraph(verxs);
        //创建一个 MinTree 对象
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, verxs, data, weight);
        //输出
        minTree.showGraph(graph);
        // 测试普利姆算法
        minTree.prim(graph, 0);
    }
}

// 创建最小生成树 -> 村庄的图
class MinTree {

    /**
     * 创建最小生成树 -> 村庄的图
     *
     * @param graph  图对象
     * @param verxs  图对应的顶点个数
     * @param data   图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph, int verxs, char[] data, int[][] weight) {
        int i, j;

        // 遍历顶点个数，开始赋值
        for (i = 0; i < verxs; i++) {
            graph.data[i] = data[i];

            // 赋值路径权值
            for (j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    // 显示图的邻接矩阵
    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 得到最小生成树
     *
     * @param graph
     * @param v
     */
    public void prim(MGraph graph, int v) {
        //visited[] 标记结点(顶点)是否被访问过
        // visited[] 默认元素的值都是 0, 表示没有访问过
        int visited[] = new int[graph.verxs];

        // 将当前结点标记为已访问
        visited[v] = 1;
        // 记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000; //将 minWeight 初始成一个大数，后面在遍历过程中，会被替换

        // 因为有 graph.verxs 个顶点，依据普利姆算法，会有 graph.verxs-1 边
        for (int k = 1; k < graph.verxs; k++) {

            // 这个是确定每一次生成的子图 ，和哪个结点的距离最近
            for (int i = 0; i < graph.verxs; i++) {
                for (int j = 0; j < graph.verxs; j++) {

                    // 当前结点没有被访问过就直接找下一个节点
                    if(visited[i] != 1) {
                        break;
                    }

                    if (visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            //找到一条边是最小
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值:" + minWeight);
            //将当前这个结点标记为已经访问
            visited[h2] = 1;
            // minWeight 重新设置为最大值 10000
            minWeight = 10000;
        }
    }
}

class MGraph {
    int verxs;  // 表示图的节点个数
    char[] data;    // 存放节点数据
    int[][] weight; // 存放边，就是我们的邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }
}

