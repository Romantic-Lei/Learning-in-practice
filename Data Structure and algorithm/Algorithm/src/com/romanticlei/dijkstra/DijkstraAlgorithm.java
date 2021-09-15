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

        graph.dsj(6);
        graph.showDijkstra();
    }
}

class Graph {
    private char[] vertexs; // 顶点数组
    private int[][] matrix; // 邻接矩阵
    private VisitedVertex vv; // 已经访问顶点的集合

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

    public void showDijkstra() {
        vv.show();
    }

    /**
     * 迪杰斯特拉算法实现
     * @param index 表示出发顶点对应的下标
     */
    public void dsj(int index) {
        vv = new VisitedVertex(vertexs.length, index);
        // 更新 index 顶点到周围顶点的距离和前驱顶点
        update(index);
        for (int i = 1; i < vertexs.length; i++) {
            // 选择并返回新的访问顶点
            index = vv.updateArr();
            // 更新 index 顶点到周围顶点的距离和前驱顶点
            update(index);
        }
    }

    /**
     * 更新 index 下标顶点到周围顶点的距离和周围顶点的前驱顶点
     * 广度优先思想
     * @param index
     */
    public void update(int index) {
        int len = 0;
        // 根据遍历我们的邻接矩阵的 matrix[index]行
        for (int j = 0; j < matrix[index].length; j++) {
            // len 含义是 出发顶点到 index 顶点的距离 + 从index 顶点到j 顶点的距离的和
            len = vv.getDis(index) + matrix[index][j];
            // 如果 j 顶点没有被访问过，并且 len 小于出发顶点到j顶点的距离，需要更新
            if (!vv.isVisted(j) && len < vv.getDis(j)) {
                // 更新j 顶点的前驱为 index顶点
                vv.updatePre(j, index);
                // 更新出发顶点到j 顶点的距离
                vv.updateDis(j, len);
            }
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
        // 设置出发顶点被访问过
        this.already_arr[index] = 1;
        // 设置出发顶点的访问距离为 0
        this.dis[index] = 0;
    }

    /**
     * 判断当前下标对应的顶点是否被访问过
     * @param index 当前顶点的下标
     * @return 如果访问过就返回 true
     */
    public boolean isVisted(int index) {
        return already_arr[index] == 1;
    }

    /**
     * 更新出发顶点到 index下标对应顶点的距离
     * @param index 当前顶点的下标
     * @param len 出发点到当前顶点的下标
     */
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    /**
     * 更新 pre 这个顶点的前驱顶点为 index 顶点
     * @param pre   当前顶点
     * @param index 当前顶点的前去顶点
     */
    public void updatePre(int pre, int index) {
        pre_visited[pre] = index;
    }

    /**
     * 返回出发点到 index顶点的距离
     * @param index
     * @return
     */
    public int getDis(int index) {
        return dis[index];
    }

    /**
     * 继续选择并返回新的访问结点，比如这里的G 点访问完后，就是A 点作为新的访问顶点
     * @return
     */
    public int updateArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < already_arr.length; i++) {
            if (already_arr[i] == 0 && dis[i] < min) {
                min = dis[i];
                index = i;
            }
        }

        // 更新 index 顶点被访问过
        already_arr[index] = 1;
        return index;
    }

    public void show() {
        System.out.println("==========================");
        //输出 already_arr
        // already_arr :1 1 1 1 1 1 1
        System.out.print("already_arr :");
        for(int i : already_arr) {
            System.out.print(i + " ");
        }
        System.out.println();

        // 输出 pre_visited
        // pre_visited : 6 6 0 5 6 6 0
        System.out.print("pre_visited : ");
        for (int i :  pre_visited) {
            System.out.print(i + " ");
        }
        System.out.println();

        // 输出 dis
        // dis : 2 3 9 10 4 6 0
        System.out.print("dis : ");
        for (int i : dis) {
            System.out.print(i + " ");
        }
        System.out.println();

        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        for (int i = 0; i < dis.length; i++) {
            if (dis[i] != 65535) {
                // A(2)B(3)C(9)D(10)E(4)F(6)G(0)
                System.out.print(vertex[i] + "(" + dis[i] + ")");
            } else {
                System.out.println("N");
            }
        }
        System.out.println();
    }
}
