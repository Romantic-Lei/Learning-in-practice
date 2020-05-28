package com.luojia.absFactory.order;

import com.luojia.absFactory.pizza.BJCheesePizza;
import com.luojia.absFactory.pizza.BJPepperPizza;
import com.luojia.absFactory.pizza.Pizza;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class BJFactory implements AbsFactory {
    @Override
    public Pizza createPizza(String orderType) {
        Pizza pizza = null;
        if ("cheese".equals(orderType)){
            pizza = new BJCheesePizza();
        } else if("pepper".equals(orderType)){
            pizza = new BJPepperPizza();
        }
        return pizza;
    }
}
