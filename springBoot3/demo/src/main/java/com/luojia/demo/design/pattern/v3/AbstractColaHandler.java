package com.luojia.demo.design.pattern.v3;

import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractColaHandler implements InitializingBean {

    public void getCoca(String parameter) {
        throw new UnsupportedOperationException();
    }

    // 模板方法1，按照业务名字可乐自己单独实现
    public String cocaMethod(String name) {
        throw new UnsupportedOperationException();
    }

    // 模板方法2，按照业务名字百事可乐自己单独实现
    public String pepsiMethod(String name) {
        throw new UnsupportedOperationException();
    }

    // 模板方法3，按照业务名字娃哈哈自己单独实现
    public String wahahaMethod(String name) {
        throw new UnsupportedOperationException();
    }

    protected void initReSource() {
        System.out.println("init抽象类父类已经统一实现，子类也可以重写");
    }

    // 保留给子类实现
    public abstract String invokeCommon();
}
