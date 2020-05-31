package com.luojia.facade;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class DVDPlayer {

    // 使用单例模式，使用饿汉模式
    private static DVDPlayer instance = new DVDPlayer();

    private DVDPlayer() { }

    public static DVDPlayer getInstance(){
        return instance;
    }

    public void on(){
        System.out.println(" dvd on");
    }

    public void off(){
        System.out.println(" dvd off");
    }

    public void play(){
        System.out.println(" dvd play");
    }

    public void pause(){
        System.out.println(" dvd pause");
    }

}
