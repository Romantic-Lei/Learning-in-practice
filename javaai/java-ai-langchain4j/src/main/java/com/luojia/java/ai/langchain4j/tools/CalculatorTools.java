package com.luojia.java.ai.langchain4j.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {

    /**
     * 可以定义成公有也可以定义成私有
     * 有时候我们定义的方式名，大模型读不懂，就需要使用@Tool里面的 name和 Value
     * @param a
     * @param b
     * @return
     */
    @Tool(name = "加法运算", value = "将两个参数a和b相加并返回运算结果")
    double add(
            @P(value = "加数1", required = true) double a,
            @P(value = "加数2", required = false) double b) {
        System.out.println("调用加法计算");
        return a + b;
    }

    @Tool
    double squareRoot(double x) {
        System.out.println("调用平方根计算");
        return Math.sqrt(x);
    }
}
