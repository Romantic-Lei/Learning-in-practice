package com.luojia.reflect;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Car {

    private String name;
    private int age;
    private String color;

    // 普通方法
    private void run() {
        System.out.println("私有方法-run");
    }

    public Car() {
    }

    public Car(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }

}
