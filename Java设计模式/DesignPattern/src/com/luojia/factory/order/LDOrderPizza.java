package com.luojia.factory.order;

import com.luojia.factory.pizza.BJCheesePizza;
import com.luojia.factory.pizza.LDCheesePizza;
import com.luojia.factory.pizza.LDPepperPizza;
import com.luojia.factory.pizza.Pizza;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class LDOrderPizza extends OrderPizza {
    @Override
    Pizza createPizza(String orderType) {
        Pizza pizza = null;

        if ("cheese".equals(orderType)){
            pizza = new LDCheesePizza();
        }else if("pepper".equals(orderType)){
            pizza = new LDPepperPizza();
        }

        return pizza;
    }
}
