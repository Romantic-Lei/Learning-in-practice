package com.romanticlei.study.interview.spring.circulardepend.constructorinjection;

import org.springframework.stereotype.Component;

@Component
public class ServiceB {

    private ServiceA serviceA;

    public ServiceB(ServiceA serviceA) {
        this.serviceA = serviceA;
    }
}
