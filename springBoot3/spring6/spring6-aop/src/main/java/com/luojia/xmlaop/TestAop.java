package com.luojia.xmlaop;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAop {

    @Test
    public void testAdd() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("beanxmlaop.xml");

        Calculator calculator = context.getBean(Calculator.class);
        calculator.add(8, 9);
    }
}
