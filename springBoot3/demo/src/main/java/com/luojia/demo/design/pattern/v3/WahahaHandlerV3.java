package com.luojia.demo.design.pattern.v3;

import org.springframework.stereotype.Component;

@Component
public class WahahaHandlerV3 extends AbstractColaHandler{

    @Override
    public void getCoca(String parameter) {
        System.out.println("我是娃哈哈+策略+工厂+模板" + parameter);
    }

    @Override
    public String wahahaMethod(String name) {
        return "娃哈哈 CocaHandlerV3 独有";
    }

    @Override
    public String invokeCommon() {
        return "我是 WahahaHandlerV3 统一实现抽象父类的invokeCommon方法";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        FactoryV3.register("wahaha", this);
    }
}
