package com.luojia.decorator;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description
 */
public class Coffee extends Drink {
    @Override
    public float cost() {
        return super.getPrice();
    }
}
