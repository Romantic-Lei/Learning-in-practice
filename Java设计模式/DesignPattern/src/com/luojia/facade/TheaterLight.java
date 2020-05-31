package com.luojia.facade;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class TheaterLight {

    private static TheaterLight instance = new TheaterLight();

    private TheaterLight() {
    }

    public static TheaterLight getInstance() {
        return instance;
    }

    public void on() {
        System.out.println(" TheaterLight up");
    }

    public void off() {
        System.out.println(" TheaterLight off");
    }

    public void bright() {
        System.out.println(" TheaterLight bright...");
    }

    public void dim() {
        System.out.println(" TheaterLight bright...");
    }

}
