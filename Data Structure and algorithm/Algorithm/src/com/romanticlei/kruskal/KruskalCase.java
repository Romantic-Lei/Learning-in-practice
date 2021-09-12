package com.romanticlei.kruskal;

import java.util.Arrays;

public class KruskalCase {

    private int edgeNum;    // 边的个数
    private char[] vertexs; // 顶点数组
    private int[][] matrix; // 邻接矩阵
    // 使用 INF 表示两个顶点不能连通
    private static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        //克鲁斯卡尔算法的邻接矩阵
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ { 0,   12, INF, INF, INF, 16,  14},
                /*B*/ { 12,  0,   10, INF, INF,  7, INF},
                /*C*/ { INF, 10,   0,   3,   5,  6, INF},
                /*D*/ { INF, INF,  3,   0,   4,INF, INF},
                /*E*/ { INF, INF,  5,   4,   0,  2,   8},
                /*F*/ { 16,    7,  6, INF,   2,  0,   9},
                /*G*/ { 14, INF,  INF, INF,  8,  9,   0}};

        // 创建 KruskalCase 对象实例
        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        kruskalCase.print();

        EData[] edges = kruskalCase.getEdges();
        System.out.println("排序前 = " + Arrays.toString(edges));
        kruskalCase.sortEdges(edges);
        System.out.println("排序后 = " + Arrays.toString(edges));

        kruskalCase.kruskal();

    }

    public KruskalCase(char[] vertexs, int[][] matrix) {
        // 初始化顶点数和个数
        int vlen = vertexs.length;

        // 初始化顶点，复制拷贝的方式
        this.vertexs = new char[vlen];
        for (int i = 0; i < vlen; i++) {
            this.vertexs[i] = vertexs[i];
        }

        //初始化边, 使用的是复制拷贝的方式
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];

                // 二维数组右三角部分统计边的条数
                if (j > i && this.matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    public void kruskal() {
        int index = 0;  // 表示最后结果数组的索引
        // 用于保存 "已有最小生成树" 中的每个顶点在最小生成树中的终点
        int[] ends = new int[edgeNum];
        // 创建结果数组，保存最后的最小生成树
        EData[] rets = new EData[edgeNum];

        // 获取图中所有边的集合，一共有 12 条边
        EData[] edges = getEdges();
        System.out.println("图的边的集合 = " + Arrays.toString(edges) + "共" + edges.length);

        // 按照边的权值大小进行排序
        sortEdges(edges);

        // 遍历 edges 数组，将边添加到最小生成树中时，判断准备加入的边是否形成回路，如果没有加入 rets，否则不加入
        for (int i = 0; i < edgeNum; i++) {
            // 获取到第 i 条边的第一个顶点（起点）
            int p1 = getPosition(edges[i].start);
            // 获取到第 i 条边的第二个顶点
            int p2 = getPosition(edges[i].end);

            // 获取p1这个顶点在已有最小生成树中的终点
            int m = getEnd(ends, p1);
            int n = getEnd(ends, p2);

            // 例如现在是A->B->C，现在来了一条边C->A,通过getEnd方法获取C顶点的终点，由于C 顶点没有被放入过数组，获取到为0
            // 接着继续通过getEnd方法获取A顶点的终点，获取到B下标，继续循环获取到C下标，由于C 顶点没有被放入过数组，获取到为0
            // 所以判定构成了回路，不会加入到 最小生成树中
            // 未构成回路
            if (m != n) {
                // 设置 m 在已有最小生成树中的终点
                ends[m] = n;
                // 有一条边加入到 rets 数组
                rets[index++] = edges[i];
            }
        }

        // 统计并打印 最小生成树，输出 rets
        System.out.println("最小生成树");
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
        }
    }

    // 打印邻接矩阵
    public void print() {
        System.out.println("邻接矩阵为：" );
        for (int[] temp : this.matrix) {
            System.out.println(Arrays.toString(temp));
        }
    }

    /**
     * 对边进行排序处理，冒泡排序
     * @param edges 边的集合
     */
    private void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    EData temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }

    /**
     *
     * @param ch 顶点的值，比如 'A', 'B'
     * @return   返回ch顶点对应的下标，如果找不到，返回-1
     */
    public int getPosition(char ch) {
        for (int i = 0; i < this.vertexs.length; i++) {
            if (vertexs[i] == ch) {
                return i;
            }
        }
        // 找不到返回 -1
        return -1;
    }

    /**
     * 获取图中的边， 放到 EData[] 数组中，后面我们需要遍历该数组
     * 是通过 matrix 邻接矩阵来获取
     * EData[] 形式[['A', 'B', 12], ['B', 'F', 7], ...]
     *
     * @return
     */
    public EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            // 取二维数组右三角
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }

        return edges;
    }

    /**
     * 例如现在是A->B->C，现在来了一条边C->A,通过getEnd方法获取C顶点的终点，由于C 顶点没有被放入过数组，获取到为0
     * 接着继续通过getEnd方法获取A顶点的终点，获取到B下标，继续循环获取到C下标，由于C 顶点没有被放入过数组，获取到为0
     *
     * 获取下标为 i 的顶点的终点(), 用于后面判断两个顶点的终点是否相同
     * @param ends: 数组就是记录了各个顶点对应的终点是哪个,ends 数组是在遍历过程中，逐步形成
     * @param i: 表示传入的顶点对应的下标
     * @return 返回的就是 下标为 i 的这个顶点对应的终点的下标, 一会回头还有来理解
     */
    public int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }
}

// 创建一个类EData，它的对象实例就表示一条边
class EData{
    char start; // 边的一个点
    char end;   // 边的另一个点
    int weight; // 边的权值

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EData{" +
                "start=" + start +
                ", end=" + end +
                ", weight=" + weight +
                '}';
    }
}
