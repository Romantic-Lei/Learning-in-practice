package com.luojia.interpreter;

import java.util.HashMap;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description 抽象运算符号解析器，每个运算符号都只有和自己左右两个数字有关，
 *              但左右两个数字有可能也是一个解析的结果，无论何种类型，都是Expression类的实现类
 */
public class SymbolExpression extends Expression {

    protected Expression left;
    protected Expression right;

    public SymbolExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    // 因为SymbolExpression是让其子类来实现，因此 interpreter是一个默认实现
    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return 0;
    }
}
