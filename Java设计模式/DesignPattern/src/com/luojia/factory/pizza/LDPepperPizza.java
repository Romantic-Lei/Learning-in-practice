package com.luojia.factory.pizza;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class LDPepperPizza extends Pizza {
    @Override
    public void prepare() {
        setName("伦敦的胡椒pizza");
        System.out.println(" 伦敦的胡椒pizza 准备原材料");
    }
}
