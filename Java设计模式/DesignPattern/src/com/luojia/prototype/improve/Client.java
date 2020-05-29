package com.luojia.prototype.improve;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public class Client {

    public static void main(String[] args) {
        System.out.println("原型模式完成对象的创建");

        Sheep sheep = new Sheep("tom", 1, "白色");
        Sheep sheep1 = (Sheep) sheep.clone();
        Sheep sheep2 = (Sheep) sheep.clone();
        Sheep sheep3 = (Sheep) sheep.clone();
        Sheep sheep4 = (Sheep) sheep.clone();
        Sheep sheep5 = (Sheep) sheep.clone();

        System.out.println("sheep2 = " + sheep2);
        System.out.println("sheep3 = " + sheep3);
        System.out.println("sheep4 = " + sheep4);
        System.out.println("sheep5 = " + sheep5);
    }
}
