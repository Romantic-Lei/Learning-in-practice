package com.luojia.interpreter;

import java.util.HashMap;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description
 */
public class SubExpression extends SymbolExpression {

    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }

    // 求出left 和right表达式相减后结果
    public int interpreter(HashMap<String, Integer> var){
        return super.left.interpreter(var) - super.right.interpreter(var);
    }

}
