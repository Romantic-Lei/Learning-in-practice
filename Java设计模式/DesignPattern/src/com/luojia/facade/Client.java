package com.luojia.facade;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class Client {

    public static void main(String[] args) {
        HomeTheaterFacade homeTheaterFacade = new HomeTheaterFacade();
        homeTheaterFacade.ready();
        homeTheaterFacade.play();

        homeTheaterFacade.end();
    }
}
