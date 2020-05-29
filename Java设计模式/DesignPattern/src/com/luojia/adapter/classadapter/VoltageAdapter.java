package com.luojia.adapter.classadapter;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description 适配器模式 -> 适配类
 */
public class VoltageAdapter extends Voltage implements IVoltage {
    @Override
    public int output5V() {
        // 获取到被适配电压
        int src = output();
        // 操作成现电压
        int des = src / 44;
        return des;
    }
}
