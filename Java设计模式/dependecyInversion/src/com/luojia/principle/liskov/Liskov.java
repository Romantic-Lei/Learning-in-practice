package com.luojia.principle.liskov;

public class Liskov {
    public static void main(String[] args) {
        A a = new A();
        System.out.println("11-3=" + a.Sub(11, 3));
        System.out.println("1-8=" + a.Sub(1, 8));
        System.out.println("-------------------------");
        B b = new B();
        System.out.println("11-3=" + b.Sub(11, 3));
        System.out.println("1-8=" + b.Sub(1, 8));
        System.out.println("11+3+9=" + b.Add(11, 3));
    }
}

// A类
class A{
    // 返回两个数的差
    public int Sub(int num1, int num2){
        return num1 - num2;
    }
}

// B类继承了A
class B extends  A{
    // 返回两个数的差
    public int Sub(int num1, int num2){
        return num1 + num2;
    }

    public int Add(int a, int b){
        return Sub(a, b) + 9;
    }
}
