package com.luojia.proxy.staticproxy;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class Client {

    public static void main(String[] args) {
        TeacherDao teacherDao = new TeacherDao();

        // 创建代理对象，同时将被代理对象传递给代理对象
        TeacherDaoProxy teacherDaoProxy = new TeacherDaoProxy(teacherDao);

        // 通过代理对象，调用被代理对象
        // 即：执行的是代理对象的方法，代理对象再去调用目标对象的方法
        teacherDaoProxy.teach();
    }
}
