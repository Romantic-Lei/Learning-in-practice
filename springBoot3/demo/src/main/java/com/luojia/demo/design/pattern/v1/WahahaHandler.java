package com.luojia.demo.design.pattern.v1;

import org.springframework.stereotype.Component;

@Component
public class WahahaHandler implements HandlerStrategy {

    @Override
    public void getCoca(String parameter) {
        System.out.println("对应娃哈哈的策略模式， parameter：" + parameter);
    }
}
