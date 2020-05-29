package com.luojia.builder.improve;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description 抽象的建造者
 */
public abstract class HouseBuilder {
    protected House house = new House();

    // 将建造的流程写好，抽象的方法
    public abstract void buildBasic();
    public abstract void buildWalls();
    public abstract void roofed();

    // 建造房子
    public House buildHouse(){
        return house;
    }

}
