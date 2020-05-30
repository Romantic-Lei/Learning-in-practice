package com.luojia.decorator;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description 具体的Decorator，这里就是调味品
 */
public class Chocolate extends Decorator {
    public Chocolate(Drink drink) {
        super(drink);
        setDes("巧克力");
        setPrice(3.0f); // 调味品价格
    }
}
