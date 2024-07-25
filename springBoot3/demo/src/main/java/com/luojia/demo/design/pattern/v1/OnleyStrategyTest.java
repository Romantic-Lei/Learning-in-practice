package com.luojia.demo.design.pattern.v1;

public class OnleyStrategyTest {

    public void more_IfELse(String parameter) {
        if ("Pepsi".equals(parameter)) {
            new PepsHandler().getCoca(parameter);
        } else if ("Coca".equals(parameter)) {
            new CocaHandler().getCoca(parameter);
        } else if ("Wahaha".equals(parameter)) {
            new WahahaHandler().getCoca(parameter);
        }
    }

    public static void main(String[] args) {
        new OnleyStrategyTest().more_IfELse("Wahaha");
    }
}
