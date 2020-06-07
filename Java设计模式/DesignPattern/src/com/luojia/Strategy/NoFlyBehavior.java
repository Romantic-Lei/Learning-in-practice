package com.luojia.Strategy;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description
 */
public class NoFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println(" 不会飞翔 ");
    }
}
