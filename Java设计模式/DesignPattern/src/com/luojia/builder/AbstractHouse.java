package com.luojia.builder;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description
 */
public abstract class AbstractHouse {

    // 打地基
    public abstract void buildBasic();
    // 砌墙
    public abstract void buildWalls();
    // 封顶
    public abstract void roofed();

    public void build(){
        buildBasic();
        buildWalls();
        roofed();
    }


}
