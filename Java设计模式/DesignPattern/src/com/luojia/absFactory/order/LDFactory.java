package com.luojia.absFactory.order;

import com.luojia.absFactory.pizza.*;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class LDFactory implements AbsFactory {
    @Override
    public Pizza createPizza(String orderType) {
        Pizza pizza = null;
        if ("cheese".equals(orderType)){
            pizza = new LDCheesePizza();
        } else if("pepper".equals(orderType)){
            pizza = new LDPepperPizza();
        }
        return pizza;
    }
}
