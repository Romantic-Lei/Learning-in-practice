package com.luojia.interpreter;

import java.util.HashMap;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description 加法解析器
 */
public class AddExpression extends SymbolExpression {

    public AddExpression(Expression left, Expression right){
        super(left, right);
    }

    // 处理相加
    public int interpreter(HashMap<String, Integer> var){
        // super.left.interpreter(var)：返回left表达式对应的值 如：a = 10；
        // super.right.interpreter(var)：返回right表达式对应值 如：b = 20;
        return super.left.interpreter(var) + super.right.interpreter(var);
    }
}
