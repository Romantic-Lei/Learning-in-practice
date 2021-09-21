package com.romanticlei.kmp;

public class ViolenceMatch {

    public static void main(String[] args) {
        String str1 = "阿里巴巴 阿里巴巴你阿里巴巴阿里你阿里巴巴你阿里你好";
        String str2 = "阿里巴巴你阿里你";
        int index = violenceMatch(str1, str2);
        System.out.println("idnex = " + index);
    }

    // 暴力破解算法实现
    public static int violenceMatch(String str1, String str2){
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1Len = s1.length;
        int s2Len = s2.length;
        int i = 0; // i索引指向s1
        int j = 0; // j索引指向s2

        while (i < s1Len && j < s2Len) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                // 如果没有匹配成功，（即s1[i] ！= s2[j]，令i = i - (j - 1)）
                // 让 i 后移一位，重新开始匹配(不能是i=i+1,因为i要从当前开始匹配的第一个字母位置后移一位，而不是匹配上几位发现匹配不上然后接着后移)
                i = i - (j - 1);
                j = 0;
            }
        }

        // 判断匹配成功
        if (j == s2Len) {
            return i - j;
        } else {
            return -1;
        }
    }
}
