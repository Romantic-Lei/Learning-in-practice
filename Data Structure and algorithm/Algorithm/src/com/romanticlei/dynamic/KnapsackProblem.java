package com.romanticlei.dynamic;

import java.util.Arrays;
import java.util.stream.Stream;

public class KnapsackProblem {

    public static void main(String[] args) {
        int[] w = {1, 4, 3}; // 物品的重量
        int[] val = {1500, 3000, 2000}; // 物品的价值
        int m = 4; // 背包的容量
        int n = val.length;// 物品个数 

        // 创建二维数组，v[i][j] 表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v = new int[n+1][m+1];
        int[][] path = new int[n+1][m+1];

        for (int i = 1; i < v.length; i++) {
            for (int j = 1; j < v[0].length; j++) {
                if (w[i-1] > j) {
                    v[i][j] = v[i-1][j];
                } else {
                    // v[i][j] = Math.max(v[i-1][j], val[i-1]+v[i-1][j-w[i-1]]);
                    // 为了记录商品存放到背包的情况，我们不能简单的使用上面公式
                    if (v[i-1][j] < val[i-1]+v[i-1][j-w[i-1]]) {
                        v[i][j] = val[i-1]+v[i-1][j-w[i-1]];
                        // 把当前结果的情况记录到path
                        path[i][j] = 1;
                    } else {
                        v[i][j] = v[i-1][j];
                    }
                }
            }
        }

        // 输出结果
        Stream.of(v).forEach(a -> {
            System.out.println(Arrays.toString(a));
        });

        int i = path.length - 1;// 行的最大下标
        int j = path[0].length - 1;// 列的最大下标
        while (i > 0 && j > 0) {
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入到背包\n", i);
                j -= w[i-1];
            }
            i--;
        }

    }
}
