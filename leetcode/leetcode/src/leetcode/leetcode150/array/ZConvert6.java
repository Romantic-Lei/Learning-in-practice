package leetcode.leetcode150.array;

import java.util.ArrayList;
import java.util.List;

public class ZConvert6 {

    /**
     * https://leetcode.cn/problems/zigzag-conversion/?envType=study-plan-v2&envId=top-interview-150
     * 将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。
     *
     * 比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：
     *
     * P   A   H   N
     * A P L S I I G
     * Y   I   R
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。
     * @param s
     * @param numRows
     * @return
     */
    public static String convert(String s, int numRows) {
        if(numRows < 2) {
            return s;
        }

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuilder());
        }

        int flag = -1;
        int i = 0;
        for (char c : s.toCharArray()) {
            if (0 == i || numRows-1 == i)
                flag = -flag;

            rows.get(i).append(c);
            i = i + flag;
        }

        StringBuilder sb = new StringBuilder();
        for (StringBuilder r : rows) {
            sb.append(r);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 3));
    }

}
