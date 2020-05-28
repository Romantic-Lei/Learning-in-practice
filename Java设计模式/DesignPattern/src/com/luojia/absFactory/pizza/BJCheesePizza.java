package com.luojia.absFactory.pizza;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class BJCheesePizza extends Pizza {

    @Override
    public void prepare() {
        setName("北京奶酪披萨");
        System.out.println("北京的奶酪披萨 准备原材料");
    }
}
