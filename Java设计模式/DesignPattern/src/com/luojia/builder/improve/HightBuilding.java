package com.luojia.builder.improve;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description
 */
public class HightBuilding extends HouseBuilder {


    @Override
    public void buildBasic() {

        System.out.println("高楼打地基5米");
    }

    @Override
    public void buildWalls() {

        System.out.println("高楼砌墙100cm");
    }

    @Override
    public void roofed() {

        System.out.println("高楼屋顶10cm");
    }
}
