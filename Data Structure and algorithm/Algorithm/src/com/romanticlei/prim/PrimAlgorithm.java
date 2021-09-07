package com.romanticlei.prim;

import java.util.Arrays;

public class PrimAlgorithm {

    // 创建最小生成树 -> 村庄的图
    class MinTree {

        /**
         * 创建最小生成树 -> 村庄的图
         * @param graph     图对象
         * @param verxs     图对应的顶点个数
         * @param data      图的各个顶点的值
         * @param weight    图的邻接矩阵
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
            for (int[] link: graph.weight) {
                System.out.println(Arrays.toString(link));
            }
        }

        /**
         *
         * @param graph
         * @param v
         */
        public void prim(MGraph graph, int v) {
            //visited[] 标记结点(顶点)是否被访问过
            int visited[] = new int[graph.verxs];
            // visited[] 默认元素的值都是 0, 表示没有访问过
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
}
