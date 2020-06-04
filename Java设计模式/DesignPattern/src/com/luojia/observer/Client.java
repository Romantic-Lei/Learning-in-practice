package com.luojia.observer;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 创建一个WeatherData
        WeatherData weatherData = new WeatherData();

        // 创建观察者
        CurrentConditions currentConditions = new CurrentConditions();

        // 注册到weatherData
        weatherData.registerObserver(currentConditions);

        System.out.println("通知各个注册的观察者，看看信息");
        weatherData.setData(10f, 95f, 30f);

    }
}
