package com.luojia.facade;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class Stereo {

    private static Stereo instance = new Stereo();

    private Stereo() {
    }

    public static Stereo getInstance() {
        return instance;
    }

    public void on() {
        System.out.println(" Stereo up");
    }

    public void off() {
        System.out.println(" Stereo off");
    }

    public void up() {
        System.out.println(" Stereo up");
    }

}
