package com.luojia.adapter.objectadapter;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description
 */
public class Phone {

    // 充电
    public void charging(IVoltage iVoltage) {
        if (iVoltage.output5V() == 5) {
            System.out.println("电压为5V，可以正常充电了");
        } else {
            System.out.println("电压不正常");
        }
    }
}
