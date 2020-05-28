package com.luojia.absFactory.order;

import com.luojia.absFactory.pizza.Pizza;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description 抽象工厂模式的抽象层（接口）
 */
public interface AbsFactory {

    // 让下面的工厂子类来具体实现
    public Pizza createPizza(String orderType);
}
