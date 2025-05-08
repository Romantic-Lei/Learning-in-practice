package com.luojia.java.ai.langchain4j.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class CalculatorTools {

    /**
     * 可以定义成公有也可以定义成私有
     * @param a
     * @param b
     * @return
     */
    @Tool
    double add(double a, double b) {
        System.out.println("调用加法计算");
        return a + b;
    }

    @Tool
    double squareRoot(double x) {
        System.out.println("调用平方根计算");
        return Math.sqrt(x);
    }
}
