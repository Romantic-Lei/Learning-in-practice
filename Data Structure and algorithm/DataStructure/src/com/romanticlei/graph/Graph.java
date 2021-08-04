package com.romanticlei.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Graph {

    // 存储顶点集合
    private ArrayList<String> vertexList;
    // 存储图对应的邻接矩阵(两个结点是否有边相连)
    private int[][] edges;
    // 表示边的数目
    private int numOfEdges;
    // 定义个数组 boolean[], 记录某个结点是否被访问
    private boolean[] isVisited;

    public static void main(String[] args) {
        int n = 5;
        Graph graph = new Graph(n);
        String[] Vertex = {"A", "B", "C", "D", "E"};
        for (String vertex : Vertex) {
            graph.insertVertex(vertex);
        }

        // 添加边
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);

        // 显示邻接矩阵
        graph.showGraph();
        System.out.println("深度优先遍历");
        // graph.dfs();
        System.out.println();

        System.out.println("广度优先遍历");
        graph.bfs();
        System.out.println();

        n = 8;
        graph = new Graph(n);
        Vertex = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
        for (String vertex : Vertex) {
            graph.insertVertex(vertex);
        }

        // 添加边
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.insertEdge(3, 7, 1);
        graph.insertEdge(4, 7, 1);
        graph.insertEdge(2, 5, 1);
        graph.insertEdge(2, 6, 1);
        graph.insertEdge(5, 6, 1);

        graph.showGraph();
        System.out.println("深度优先遍历");
        // 1->2->4->8->5->3->6->7->
        // graph.dfs();
        System.out.println();

        System.out.println("广度优先遍历");
        // 1=>2=>3=>4=>5=>6=>7=>8=>
        graph.bfs();
        System.out.println();

    }

    public Graph(int n) {
        // 初始化矩阵和 vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0;
        isVisited = new boolean[n];
    }

    // 插入结点
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 表示点的下标即第一个顶点
     * @param v2 表示第二个顶点的下标
     * @param weight 表示权重
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    // 显示图对应的矩阵
    public void showGraph() {
        System.out.println("二维数据遍历方法一：");
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }

        System.out.println("二维数据遍历方法二：");
        Arrays.stream(edges).forEach(a -> {
            System.out.println(Arrays.toString(a));
        });

        System.out.println("二维数据遍历方法三：");
        Stream.of(edges).forEach(a -> {
            System.out.println(Arrays.toString(a));
        });
    }

    /**
     * 得到第一个邻接结点的下标 w
     * @param index
     * @return 如果存在返回对应的下标，否则返回-1
     */
    public int getFirstNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接结点的下标来获取下一个邻接结点
     * @param v1 当前结点下标
     * @param v2 相邻结点下标
     * @return
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (edges[v1][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    // 深度优先遍历算法
    public void dfs(boolean[] isVisited, int i) {
        // 首先我们访问该结点，输出
        System.out.print(getValueByIndex(i) + "->");
        // 将该结点设置为已访问
        isVisited[i] = true;
        // 查找结点i的第一个邻接结点w
        int w = getFirstNeighbor(i);
        while (w != -1) {
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }

            // 如果w节点已经被访问过
            w = getNextNeighbor(i, w);
        }
    }

    public void dfs() {
        // 遍历所有的节点，进行dfs[回溯]
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]){
                dfs(isVisited, i);
            }
        }
    }

    public void bfs(boolean[] isVisited, int i) {
        int queueFirstNode; // 表示队列的头结点对应的下标
        int w; // 邻接结点w
        // 队列，记录结点访问顺序
        LinkedList queue = new LinkedList<>();
        // 访问结点，输出结点信息
        System.out.print(getValueByIndex(i) + "=>");
        // 标记结点为已访问
        isVisited[i] = true;
        // 将结点加入队列
        queue.addLast(i);

        while (!queue.isEmpty()) {
            queueFirstNode = (Integer) queue.removeFirst();
            w = getFirstNeighbor(queueFirstNode);
            while (w != -1) {
                // 找到邻接结点
                if (!isVisited[w]) {
                    // 没有被访问过
                    System.out.print(getValueByIndex(w) + "=>");
                    // 标记已经访问过
                    isVisited[w] = true;
                    queue.addLast(w);
                }

                // 以queueFirstNode 为前驱，找w后面的下一个邻接结点
                w = getNextNeighbor(queueFirstNode, w);
            }
        }
    }
    
    // 遍历所有结点，进行广度优先搜索
    public void bfs() {
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
    }

    // 返回结点的个数
    public int getNumOfVertex(){
        return vertexList.size();
    }

    // 得到边的数目
    public int getNumOfEdges(){
        return numOfEdges;
    }

    // 返回结点i(下标)对应的数据 0->"A", 1->"B", 2->"C"
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    // 返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

}
