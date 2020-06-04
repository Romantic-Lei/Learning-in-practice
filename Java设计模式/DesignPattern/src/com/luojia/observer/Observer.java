package com.luojia.observer;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/3
 * @description 观察者接口，有观察者来实现
 */
public interface Observer {

    public void update(float temperature, float pressure, float humidity);

}
