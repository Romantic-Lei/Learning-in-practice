package com.luojia.adapter.objectadapter;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description 适配器模式 -> 被适配类
 */
public class Voltage {

    public int output(){
        int src = 220;
        System.out.println("电压 = " + src);
        return src;
    }
}
