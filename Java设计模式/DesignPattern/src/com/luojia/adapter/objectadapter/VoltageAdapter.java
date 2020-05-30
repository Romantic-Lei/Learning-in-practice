package com.luojia.adapter.objectadapter;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description 适配器模式 -> 适配类
 */
public class VoltageAdapter implements IVoltage {

    private Voltage voltage;

    public VoltageAdapter(Voltage voltage) {
        this.voltage = voltage;
    }

    @Override
    public int output5V() {
        int dst = 0;
        if(null != voltage){
            int src = voltage.output();// 获取转换前的数据
            System.out.println("使用对象适配器，进行适配");
            dst = src/44;
            System.out.println("适配完成，输出的电压为=" + dst);
        }
        return dst;
    }
}
