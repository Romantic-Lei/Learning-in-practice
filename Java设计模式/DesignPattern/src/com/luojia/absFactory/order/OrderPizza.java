package com.luojia.absFactory.order;

import com.luojia.absFactory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class OrderPizza {

    AbsFactory absFactory;

    public OrderPizza(AbsFactory AbsFactory){
        setAbsFactory(AbsFactory);
    }

    private void setAbsFactory(AbsFactory absFactory) {
        Pizza pizza = null;
        String orderType = ""; // 用户输入
        this.absFactory = absFactory;

        do{
            orderType = getType();
            // absFactory 可能是北京的工厂子类，也可能是伦敦的工厂子类
            pizza = absFactory.createPizza(orderType);
            if (pizza != null){
                pizza.prepare();
                pizza.bake();
                pizza.cut();
                pizza.box();
            }else{
                System.out.println("订购失败");
                break;
            }

        }while(true);

    }

    private String getType() {
        try {
            BufferedReader strin = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("input pizza 种类");
            String str = strin.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return  "";
        }
    }
}
