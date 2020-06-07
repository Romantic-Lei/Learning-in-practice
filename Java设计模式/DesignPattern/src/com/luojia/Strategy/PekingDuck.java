package com.luojia.Strategy;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description
 */
public class PekingDuck extends Duck {


    public PekingDuck() {
        flyBehavior =  new BadFlyBehavior();
    }

    @Override
    public void display() {
        System.out.println(" 北京鸭 ");
    }

}
