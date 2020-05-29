package com.luojia.builder.improve;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description
 */
public class CommonHouse extends HouseBuilder {


    @Override
    public void buildBasic() {
        System.out.println("普通房子打地基5米");
    }

    @Override
    public void buildWalls() {
        System.out.println("普通房子砌墙10cm");
    }

    @Override
    public void roofed() {
        System.out.println("普通房子屋顶5cm");
    }
}
