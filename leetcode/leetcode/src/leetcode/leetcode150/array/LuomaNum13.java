package leetcode.leetcode150.array;

import java.util.HashMap;
import java.util.Map;

public class LuomaNum13 {

    /**
     * https://leetcode.cn/problems/roman-to-integer/description/?envType=study-plan-v2&envId=top-interview-150
     * 13. 罗马数字转整数
     * 小的数在大数左边，是大数-小数
     * 小的数在大数右边，是大数+小数
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        int length = s.length();
        int sum = 0;
        for (int i = 0; i < length; i++) {
            Integer left = symbolValues.get(s.charAt(i));
            // 保证最后一位能被加上。同时symbolValues.get(s.charAt(i + 1)) 不提钱取出保证也不会越界
            if (i < length - 1 && left < symbolValues.get(s.charAt(i + 1))) {
                sum -= left;
            } else {
                sum += left;
            }
        }
        return sum;
    }

    static Map<Character, Integer> symbolValues = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    public static void main(String[] args) {
        System.out.println(romanToInt("MCMXCIV"));
    }

}
