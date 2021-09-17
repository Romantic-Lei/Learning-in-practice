package com.romanticlei.floyd;

import java.util.Arrays;

public class FloydAlgorithm {

    public static void main(String[] args) {
        // 测试看看图是否创建成功
        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        //创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
        matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
        matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
        matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
        matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
        matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
        matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };
        Graph graph = new Graph(vertex.length, matrix, vertex);

        graph.floyd();
        graph.show();
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

    public void show() {
        for (int k = 0; k < dis.length; k++) {
            // 先将 pre 数组输出一行
            for (int i = 0; i < dis.length; i++) {
                System.out.print(vertexs[pre[k][i]] + " ");
            }
            System.out.println();

            for (int i = 0; i < dis.length; i++) {
                System.out.print("(" + vertexs[k] + "到" + vertexs[i] + "的最短路径是" + dis[k][i] + ")");
            }
            System.out.printf("\n\n");
        }
    }

    public void floyd() {
        int len = 0; // 变量保存距离
        // 对中间顶点进行遍历，k就是中间顶点的下标[A, B, C, D, E, F, G]
        for (int k = 0; k < vertexs.length; k++) {
            // 从顶点 i 开始出发，[A, B, C, D, E, F, G]
            for (int i = 0; i < dis.length; i++) {
                // 到达顶点 j 结束 [A, B, C, D, E, F, G]
                for (int j = 0; j < dis.length; j++) {
                    // 起始顶点和中间顶点一样就没有意义
                    if (k == i) {
                        break;
                    }
                    len = dis[i][k] + dis[k][j];
                    if (len < dis[i][j]) {
                        // 更新距离
                        dis[i][j] = len;
                        // 更新前驱顶点
                        pre[i][j] = pre[k][j];
                    }
                }
            }
        }
    }

}
