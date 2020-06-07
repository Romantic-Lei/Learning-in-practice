package com.luojia.Strategy;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/7
 * @description
 */
public class Client {

    public static void main(String[] args) {
        WildDuck wildDuck = new WildDuck();
        wildDuck.fly();

        ToyDuck toyDuck = new ToyDuck();
        toyDuck.fly();

        PekingDuck pekingDuck = new PekingDuck();
        pekingDuck.fly();

        // 动态的改变某个对象的行为，北京鸭 不能飞翔
        pekingDuck.setFlyBehavior(new NoFlyBehavior());
        System.out.println("北京鸭的实际飞翔能力: ");
        pekingDuck.fly();

    }
}
