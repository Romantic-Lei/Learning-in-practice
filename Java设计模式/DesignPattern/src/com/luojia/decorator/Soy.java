package com.luojia.decorator;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description
 */
public class Soy extends Decorator {
    public Soy(Drink drink) {
        super(drink);
        setDes(" 豆浆 ");
        setPrice(1.5f);
    }
}
