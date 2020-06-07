package com.luojia.Strategy;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description
 */
public class WildDuck extends Duck {

    // 构造器，传入FlyBehavor的对象


    public WildDuck() {
        flyBehavior = new GoodFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println(" 这是野鸭 ");
    }
}
