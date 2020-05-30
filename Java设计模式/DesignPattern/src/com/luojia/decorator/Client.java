package com.luojia.decorator;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description
 */
public class Client {

    public static void main(String[] args) {

        // 1. 点一份LongBlack
        Drink order = new LongBlack();
        System.out.println("费用 = " + order.cost());
        System.out.println("描述 = " + order.getDes());

        // 2. order加入一份牛奶
        order = new Milk(order);
        System.out.println("order 加入一份牛奶的费用 = " + order.cost());
        System.out.println("order 加入一份牛奶的描述 = " + order.getDes());

        // 3. order加入一份巧克力
        order = new Chocolate(order);
        System.out.println("order 加入一份巧克力的费用 = " + order.cost());
        System.out.println("order 加入一份巧克力的描述 = " + order.getDes());

        // 4. order加入一份豆浆
        order = new Soy(order);
        System.out.println("order 加入一份豆浆的费用 = " + order.cost());
        System.out.println("order 加入一份豆浆的描述 = " + order.getDes());

    }
}
