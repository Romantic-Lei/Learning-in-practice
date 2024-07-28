package com.luojia.demo.design.pattern.v3;

import org.springframework.stereotype.Component;

@Component
public class CocaHandlerV3 extends AbstractColaHandler {
    @Override
    public void getCoca(String parameter) {
        System.out.println("我是可口可乐+策略+工厂+模板" + parameter);
    }

    @Override
    public String cocaMethod(String name) {
        return "可口可乐 CocaHandlerV3 独有";
    }

    @Override
    public String invokeCommon() {
        return "我是 CocaHandlerV3 统一实现抽象父类的invokeCommon方法";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FactoryV3.register("coca", this);
    }
}
