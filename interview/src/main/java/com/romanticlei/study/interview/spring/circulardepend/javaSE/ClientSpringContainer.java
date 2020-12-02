package com.romanticlei.study.interview.spring.circulardepend.javaSE;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientSpringContainer {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        A a = context.getBean("a", A.class);
        B b = context.getBean("b", B.class);
    }
}
