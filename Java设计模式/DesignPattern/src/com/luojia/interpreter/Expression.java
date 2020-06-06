package com.luojia.interpreter;

import java.util.HashMap;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description 抽象类表达式，通过HashMap 键值对，可以获取到变量的值
 */
public abstract class Expression {

    // 解释公式和数值， key就是公式（表达式）参数，value就是具体值
    public abstract int interpreter(HashMap<String, Integer> var);
}
