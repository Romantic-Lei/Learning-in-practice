package com.luojia.facade;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class Popcorn {

    private static Popcorn instance = new Popcorn();

    private Popcorn() {}

    public static Popcorn getInstance(){
        return instance;
    }

    public void on(){
        System.out.println(" Popcorn on");
    }

    public void off(){
        System.out.println(" Popcorn off");
    }

    public void pop(){
        System.out.println(" Popcorn is poping...");
    }

}
