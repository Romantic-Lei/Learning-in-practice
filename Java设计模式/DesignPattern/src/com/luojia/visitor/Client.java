package com.luojia.visitor;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 创建ObjectStructure
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.attach(new Man());
        objectStructure.attach(new Woman());

        // 成功
        Success success = new Success();
        objectStructure.display(success);

        System.out.println("---------------------------------");

        Fail fail = new Fail();
        objectStructure.display(fail);

    }
}
