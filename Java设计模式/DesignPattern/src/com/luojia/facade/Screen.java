package com.luojia.facade;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class Screen {

    private static Screen instance = new Screen();

    private Screen() {
    }

    public static Screen getInstance() {
        return instance;
    }

    public void up() {
        System.out.println(" Screen up");
    }

    public void down() {
        System.out.println(" Screen down");
    }

}
