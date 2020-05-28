package com.luojia.factory.order;

import com.luojia.factory.pizza.Pizza;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public abstract class OrderPizza {

    // 构造器
    public OrderPizza(){
        Pizza pizza = null;
        String orderType; // 订购披萨的类型
        do{
            orderType = getType();
            pizza = createPizza(orderType);// 抽象方法，由工厂子类完成
            // 输出pizza制作过程
            pizza.prepare();
            pizza.bake();
            pizza.cut();
            pizza.box();
        }while (true);
    }

    // 定义一个抽象方法，createPizza，让各个工厂子类自己实现
    abstract Pizza createPizza(String orderType);

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
