package com.romanticlei.recursion;

public class recursionTest {

    public static void main(String[] args) {
        test(4);

        int x = 5;
        int value = factorial(x);
        System.out.println("递归求阶乘-> " + x +"! = " + value);
    }

    public static void test(int n) {
        if (n > 2){
            test(n - 1);
        }
        System.out.println("n = " + n);
    }

    public static int factorial(int n){
        if (n > 1){
            return factorial(n - 1) * n;
        } else {
            return 1;
        }
    }

}
