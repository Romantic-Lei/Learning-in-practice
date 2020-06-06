package com.luojia.interpreter;

import java.util.HashMap;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description 变量的解释器
 */
public class VarExpression extends Expression {

    private String key;

    public VarExpression(String key) {
        this.key = key;
    }

    @Override
    public int interpreter(HashMap<String, Integer> var) {
        return var.get(this.key);
    }
}
