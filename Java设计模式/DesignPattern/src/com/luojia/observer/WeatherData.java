package com.luojia.observer;

import java.util.ArrayList;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description
 */
public class WeatherData implements Subject {

    // 温度，气压， 湿度
    private float temperature;
    private float pressure;
    private float humidity;

    // 观察者集合
    private ArrayList<Observer> observers;

    public WeatherData() {
        observers = new ArrayList<Observer>();
    }

    public float getTemperature() {
        return temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void dataChange(){
        // 调用接入方的update
        notityObservers();
    }

    // 当有数据更新时，就调用setData
    public void setData(float temperature, float pressure, float humidity){
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        // 调用dataChange，将最新的信息推送给接入方 currentConditions
        dataChange();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers.contains(observer)){
            observers.remove(observer);
        }
    }

    @Override
    public void notityObservers(){
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(temperature, pressure, humidity);
        }
    }
}
