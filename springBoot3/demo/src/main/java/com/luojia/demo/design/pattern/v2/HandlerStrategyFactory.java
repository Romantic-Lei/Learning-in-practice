package com.luojia.demo.design.pattern.v2;

import org.springframework.beans.factory.InitializingBean;

public interface HandlerStrategyFactory extends InitializingBean {

    void getCoca(String parameter);
}
