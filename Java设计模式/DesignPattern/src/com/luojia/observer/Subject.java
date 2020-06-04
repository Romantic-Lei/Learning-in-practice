package com.luojia.observer;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description
 */
public interface Subject {

    // 注册一个观察者
    public void registerObserver(Observer observer);

    // 移除一个观察者
    public void removeObserver(Observer observer);

    // 遍历所有的观察者，并通知
    public void notityObservers();
}
