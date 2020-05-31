package com.luojia.proxy.dynamic;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 创建目标对象
        ITeacherDao target = new TeacherDao();

        // 给目标对象创建代理对象
        ITeacherDao proxyInstance = (ITeacherDao) new ProxyFactory(target).getProxyInstance();

        System.out.println("proxyInstance = " +proxyInstance);

        // proxyInstance = class com.sun.proxy.$Proxy0-> 内存中动态生成了代理对象
        System.out.println("proxyInstance = " +proxyInstance.getClass());

        // 通过代理对象，调用目标对象的方法
        proxyInstance.teach();
    }
}
