package com.luojia.absFactory.pizza;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class BJPepperPizza extends Pizza {
    @Override
    public void prepare() {

        setName("北京的胡椒 pizza");
        System.out.println("北京的胡椒pizza 准备原材料");
    }
}
