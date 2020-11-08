package com.romanticLei;

public class StringPoolDemo {

    public static void main(String[] args) {
        String str1 = new StringBuilder("Hello").append("romanticLei").toString();
        System.out.println(str1);
        System.out.println(str1.intern());

        System.out.println();

        String str2 = new StringBuffer("ja").append("va").toString();
        System.out.println(str2);
        System.out.println(str2.intern());

    }
}
