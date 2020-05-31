package com.luojia.facade;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class Projector {

    private static Projector instance = new Projector();

    private Projector() {}

    public static Projector getInstance(){
        return instance;
    }

    public void on(){
        System.out.println(" Project on");
    }

    public void off(){
        System.out.println(" Project off");
    }

    public void focus(){
        System.out.println(" Project is focusing");
    }

}
