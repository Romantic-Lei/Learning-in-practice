package com.luojia.Strategy;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description
 */
public class ToyDuck extends Duck {

    public ToyDuck() {
        flyBehavior = new NoFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println("  玩具鸭  ");
    }
}
