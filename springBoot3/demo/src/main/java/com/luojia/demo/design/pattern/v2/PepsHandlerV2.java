package com.luojia.demo.design.pattern.v2;

import org.springframework.stereotype.Component;

@Component
public class PepsHandlerV2 implements HandlerStrategyFactory {
    @Override
    public void getCoca(String parameter) {
        System.out.println("我是百事可乐+策略+工厂" + parameter);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Factory.register("Pepsi", this);
    }
}
