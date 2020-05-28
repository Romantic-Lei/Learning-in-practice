package com.luojia.factory.pizza;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class LDCheesePizza extends Pizza {
    @Override
    public void prepare() {
        setName("伦敦的奶酪pizza");
        System.out.println(" 伦敦的奶酪pizza 准备原材料");
    }
}
