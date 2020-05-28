package com.luojia.absFactory.order;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class PizzaStore {

    public static void main(String[] args) {
        new OrderPizza(new BJFactory());
        new OrderPizza(new LDFactory());
    }
}
