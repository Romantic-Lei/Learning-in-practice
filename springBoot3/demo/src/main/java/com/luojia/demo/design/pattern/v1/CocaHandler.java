package com.luojia.demo.design.pattern.v1;

import org.springframework.stereotype.Component;

@Component
public class CocaHandler implements HandlerStrategy {

    @Override
    public void getCoca(String parameter) {
        System.out.println("对应可口可乐的策略模式， parameter：" + parameter);
    }
}
