package com.romanticlei.kmp;

import java.util.Arrays;

public class KMPAlgorithm {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String dest = "ABCDABD";

        int[] next = kmpNext("ABCDABD");
        System.out.println("next = " + Arrays.toString(next));

        int index = kmpSearch(str1, dest, next);
        System.out.println("index = " + index);
    }

    /**
     * KMP 搜索算法
     * @param str1 源字符串
     * @param desc 目标字符串
     * @param next 部分匹配表，是子串的部分匹配表
     * @return 返回-1是没有匹配到，否则返回匹配到的位置
     */
    public static int kmpSearch(String str1, String desc, int[] next){
        
        // 遍历
        for (int i = 0, j = 0; i < str1.length(); i++) {

            while (j > 0 && str1.charAt(i) != desc.charAt(j)){
                j = next[j - 1];
            }

            if (str1.charAt(i) == desc.charAt(j)){
                j++;
            }

            if (j == desc.length()) {
                return i - j + 1;
            }
        }

        return -1;
    }

    // 获取到一个字符串（子串）的部分匹配
    public static int[] kmpNext(String dest) {
        // 创建一个next 数组，保存部分匹配的值
        int[] next = new int[dest.length()];
        // 如果字符串长度时1, 部分匹配值就是0
        next[0] = 0;
        for (int i = 1, j = 0; i < dest.length(); i++) {
            while (j > 0 && dest.charAt(i) != dest.charAt(j)) {
                j = next[j - 1];
            }

            if (dest.charAt(i) == dest.charAt(j)) {
                j++;
            }
            next[i] = j;
        }

        return next;
    }
}
