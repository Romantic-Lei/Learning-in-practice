package com.luojia.proxy.cglib;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/1
 * @description CGLib动态代理
 */
public class TeacherDao {

    public String teach(){
        System.out.println(" 老师授课中， 我是CGLib代理，不需要实现接口 ");
        return "hello, cglib";
    }

}
