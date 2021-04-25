package com.romanticlei.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class PolandNotation {

    public static void main(String[] args) {

        // 完成一个中缀表达式转集合
        /**
         * 直接将字符串转成后缀表达式不方便，素偶一我们需要将字符串先转成list
         * 例如：1+((2+3)x4)-5 => 转成 [1, +, (, (, 2, +, 3, ), x, 4, ), -, 5]
         */
        List<String> infixExpressionList = toInfixExpressionList("1+((2+3)x4)-5");
        System.out.println("中缀表达式转集合之后：" + infixExpressionList);

        // 先定义一个逆波兰表达式
        // (3+4)x5-6 => 3 4 + 5 x 6 -
        // 说明：为了方便，逆波兰表达式和符号使用空格隔开
        String suffixExpression = "3 4 + 5 * 6 -";
        // 思路
        // 先将 "3 4 + 5 x 6 -" => 放到 ArrayList中
        // 将 ArrayList 传递给一个方法，然后遍历 ArrayList 配合栈完成计算

        System.out.println(getListString(suffixExpression));
        int res = calculate(getListString(suffixExpression));

        System.out.println("计算结果为： " + res);
    }

    // 将中缀表达式转成后缀表达式
    public static List<String> toInfixExpressionList(String expression) {
        int i = 0;  // 定义索引
        String str;
        char ch = ' ';
        List<String> list = new ArrayList<>();
        do {
            if ((ch = expression.charAt(i)) < 48 || (ch = expression.charAt(i)) > 57) {
                list.add(String.valueOf(ch));
                i++;
            } else {
                str = "";
                while (i < expression.length() && (ch = expression.charAt(i)) >= 48 && (ch = expression.charAt(i)) <= 57){
                    str += ch;
                    i++;
                }
                list.add(str);
            }
        } while (i < expression.length());

        return list;
    }


    // 将一个逆波兰表达式，依次将数据和运算符 放入到 ArrayList中
    public static List<String> getListString(String suffixExpression) {
        String[] split = suffixExpression.split(" ");
        List<String> list = Arrays.asList(split);

        return list;
    }

    // 完成对逆波兰表达式的运算
    public static int calculate(List<String> list) {
        Stack<String> stack = new Stack<>();
        // 遍历集合 list
        for (String item : list) {
            // 判断是否是数字
            if (item.matches("\\d+")) {
                stack.push(item);
            } else {
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if ("+".equals(item)) {
                    res = num1 + num2;
                } else if ("-".equals(item)) {
                    res = num1 - num2;
                } else if ("*".equals(item)) {
                    res = num1 * num2;
                } else if ("/".equals(item)) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("无法识别的运算符");
                }
                stack.push(String.valueOf(res));
            }
        }
        return Integer.parseInt(stack.pop());
    }
}


