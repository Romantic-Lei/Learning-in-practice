package com.luojia.template;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/1
 * @description
 */
public class Client {

    public static void main(String[] args) {

        // 制作豆浆
        System.out.println("-----制作红豆豆浆-----");
        RedBeanSoyaMilk redBeanSoyaMilk = new RedBeanSoyaMilk();
        redBeanSoyaMilk.make();

        System.out.println("-----制作花生豆浆-----");
        PeanutSoyaMilk peanutSoyaMilk = new PeanutSoyaMilk();
        peanutSoyaMilk.make();

    }
}
