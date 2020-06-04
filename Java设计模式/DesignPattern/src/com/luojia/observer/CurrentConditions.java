package com.luojia.observer;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description
 */
public class CurrentConditions implements Observer {

    // 温度，气压， 湿度
    private float temperature;
    private float pressure;
    private float humidity;

    public void update(float temperature, float pressure, float humidity){
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        display();
    }

    private void display() {
        System.out.println("***Today mTemperature" + temperature + "***");
        System.out.println("***Today mPressure" + pressure + "***");
        System.out.println("***Today mHumidity" + humidity + "***");
    }

}
