package com.romanticLei;

public class StringPoolDemo {

    public static void main(String[] args) {
        String str1 = new StringBuilder("Hello").append("RomanticLei").toString();
        // HelloRomanticLei
        System.out.println(str1);
        // HelloRomanticLei
        System.out.println(str1.intern());
        // true
        System.out.println(str1 == str1.intern());

        System.out.println();

        String str2 = new StringBuilder("ja").append("va").toString();
        // java
        System.out.println(str2);
        // java
        System.out.println(str2.intern());
        // false
        System.out.println(str2 == str2.intern());

    }
}
