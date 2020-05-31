package com.luojia.flyweight;

import java.util.HashMap;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description 网站工厂类，根据需求返回一个网站
 */
public class WebSiteFactory {

    // 集合，充当池的作用
    private HashMap<String, ConcreateWbeSite> pool = new HashMap<>();

    // 根据网站的类型，返回一个网站，如果没有就创建一个网站，并放入到池中
    public WebSite getWebSiteCategory(String type) {
        if (!pool.containsKey(type)) {
            // 就创建一个网站，并放入到池中
            pool.put(type, new ConcreateWbeSite(type));
        }

        return pool.get(type);
    }

    // 获取网站分类的总数（池中有多少个网站类型）
    public int getWebSiteCount() {
        return pool.size();
    }

}
