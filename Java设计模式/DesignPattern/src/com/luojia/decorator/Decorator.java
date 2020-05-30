package com.luojia.decorator;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description 咖啡调料
 */
public class Decorator extends Drink {

    private Drink drink;

    public Decorator(Drink drink) {
        this.drink = drink;
    }

    @Override
    public float cost() {
        // getPrice 自己价格
        return super.getPrice() + drink.cost();
    }

    @Override
    public String getDes() {
        return super.des + " " + super.getPrice() + "&&" + drink.getDes();
    }
}
