package com.luojia.flyweight;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class ConcreateWbeSite extends WebSite {

    private String type = ""; //网站发布的形式（类型）

    public ConcreateWbeSite(String type) {
        this.type = type;
    }

    @Override
    public void use(User user) {
        System.out.println("网站的发布形式为： " + type + " 使用者为" + user.getName());
    }
}
