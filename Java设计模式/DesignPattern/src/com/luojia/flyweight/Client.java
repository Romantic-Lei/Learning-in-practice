package com.luojia.flyweight;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 创建一个工厂类
        WebSiteFactory factory = new WebSiteFactory();

        // 客户要以一个新闻形式发布的网站
        WebSite webSite = factory.getWebSiteCategory("新闻");

        // 客户要以一个博客形式发布的网站
        WebSite webSite2 = factory.getWebSiteCategory("博客");

        WebSite webSite3 = factory.getWebSiteCategory("博客");

        webSite.use(new User("Tom"));
        webSite2.use(new User("Jack"));
        webSite3.use(new User("爱丽丝"));

        System.out.println("使用到的对象有" + factory.getWebSiteCount() + "个");
    }

}
