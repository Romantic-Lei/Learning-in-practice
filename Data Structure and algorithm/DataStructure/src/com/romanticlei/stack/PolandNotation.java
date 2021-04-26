package com.romanticlei.stack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

public class PolandNotation {

    public static void main(String[] args) {

        // 完成一个中缀表达式转集合
        /**
         * 直接将字符串转成后缀表达式不方便，素偶一我们需要将字符串先转成list
         * 例如：1+((2+3)x4)-5 => 转成 [1, +, (, (, 2, +, 3, ), x, 4, ), -, 5]
         */
        String expreesion = "1+ ((21.53+ 3)* " + "\t\n" + "4)-5";
        expreesion = replaceAllBlank(expreesion);

        List<String> infixExpressionList = toInfixExpressionList(expreesion);
        System.out.println("中缀表达式转集合之后：" + infixExpressionList);

        /**
         * 将得到的中缀表达式list => 转成后缀表达式的list
         */
        List<String> parseSuffixExpreesionList = parseSuffixExpreesionList(infixExpressionList);
        System.out.println("后缀表达式对应的集合：" + parseSuffixExpreesionList);
        System.out.println("中缀表达式转后缀表达式计算结果~:  " + calculate(parseSuffixExpreesionList));

        System.out.println("/* -------------------------------------------------------------*/");
        /* -------------------------------------------------------------*/
        // 先定义一个逆波兰表达式
        // (3+4)x5-6 => 3 4 + 5 x 6 -
        // 说明：为了方便，逆波兰表达式和符号使用空格隔开
        String suffixExpression = "3 4 + 5 * 6 -";
        // 思路
        // 先将 "3 4 + 5 x 6 -" => 放到 ArrayList中
        // 将 ArrayList 传递给一个方法，然后遍历 ArrayList 配合栈完成计算

        System.out.println(getListString(suffixExpression));
        double res = calculate(getListString(suffixExpression));

        System.out.println("计算结果为： " + res);
    }

    // 将需要转换的字符串过滤，去掉空白符等
    public static String replaceAllBlank(String expression) {
        // \\s+ 匹配任何空白字符，包括空格、制表符、换页符等等, 等价于[ \f\n\r\t\v]
        return expression.replaceAll("\\s+", "");
    }

    // 将中缀表达式转成集合的形式
    public static List<String> toInfixExpressionList(String expression) {
        int i = 0;  // 定义索引
        String str;
        char ch = ' ';
        List<String> list = new ArrayList<>();
        do {
            // 第一位不是数字时直接抛出异常
            if (i == 0) {
                ch = expression.charAt(i);
                try {
                    Integer.parseInt(String.valueOf(ch));
                } catch (Exception e) {
                    throw new RuntimeException("data illeagle,start not with a number");
                }
            }

            if ((ch = expression.charAt(i)) < 48 || (ch = expression.charAt(i)) > 57) {
                // 符号直接入栈
                list.add(String.valueOf(ch));
                i++;
            } else {
                str = "";
                while (i < expression.length() && (ch = expression.charAt(i)) >= 48 && (ch = expression.charAt(i)) <= 57) {
                    // 如果是数字则判断下一位是否依旧是数字，然后进行数字拼接操作
                    str += ch;
                    i++;
                }
                // 将拼接后的数字入栈
                list.add(str);
            }
        } while (i < expression.length());

        return list;
    }

    // 中缀表达式集合转后缀表达式集合
    public static List<String> parseSuffixExpreesionList(List<String> list) {
        // 符号栈
        Stack<String> s1 = new Stack<>();
        // 后缀表达式集合
        List<String> s2 = new ArrayList<>();
        for (String item : list) {
            // 匹配多位数字入栈
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if ("(".equals(item)) {
                s1.push(item);
            } else if (")".equals(item)) {
                // 如果当前为右括号，就匹配到距栈顶最近的一个左括号为止
                while (!"(".equals(s1.peek())) {
                    s2.add(s1.pop());
                }
                // 如果弹出的是 "(" 那就直接清除 "("
                s1.pop();
            } else {
                // 当 item 的优先级小于等于s1 栈顶运算符，那么就需要将s1栈顶的数据弹出，直到item 运算符优先级高或者s1栈顶为空，item 入栈
                while (s1.size() > 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    if (".".equals(s1.peek())) {
                        int len = s2.size();
                        String num1 = s2.get(len - 1);
                        String num2 = s2.get(len - 2);
                        String symbol = s1.pop();
                        String newNum = num2 + symbol + num1;
                        s2.set(len - 2, newNum);
                        s2.remove(len - 1);

                    } else {
                        s2.add(s1.pop());
                    }
                }

                // 将 item 放入大栈顶
                s1.push(item);
            }
        }

        // 将s1 中剩余的运算符一次弹出并加入到 s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }

        return s2;
    }

    // 将一个逆波兰表达式，依次将数据和运算符 放入到 ArrayList中
    public static List<String> getListString(String suffixExpression) {
        String[] split = suffixExpression.split(" ");
        List<String> list = Arrays.asList(split);

        return list;
    }

    // 完成对逆波兰表达式的运算
    public static double calculate(List<String> list) {
        Stack<String> stack = new Stack<>();
        // 遍历集合 list
        for (String item : list) {
            // 判断是否是数字
            if (item.matches("^[0-9]+([.]{1}[0-9]+){0,1}$")) {
                stack.push(item);
            } else {
                // int num2 = Integer.parseInt(stack.pop());
                // int num1 = Integer.parseInt(stack.pop());.
                // int res = 0;
                Double num2 = Double.parseDouble(stack.pop());
                Double num1 = Double.parseDouble(stack.pop());
                double res = 0;
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
        // return Integer.parseInt(stack.pop());
        return Double.parseDouble(stack.pop());
    }
}

// 编写一个类 Operation，可以返回一个运算符对应的优先级
class Operation {
    private static int LEFT = 0;
    private static int RIGHT = 0;
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    // 判断对应运算符的优先级
    public static int getValue(String operation) {
        switch (operation) {
            case "(":
                return LEFT;
            case ")":
                return RIGHT;
            case "+":
                return ADD;
            case "-":
                return SUB;
            case "*":
                return MUL;
            case "/":
                return DIV;
            default:
                // System.out.println("运算符不存在");
                return Integer.MAX_VALUE;
        }
    }
}


