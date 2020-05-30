package com.luojia.decorator;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description
 */
public class Milk extends Decorator {

    public Milk(Drink drink) {
        super(drink);
        setDes(" 牛奶 ");
        setPrice(2.0f);
    }
}
