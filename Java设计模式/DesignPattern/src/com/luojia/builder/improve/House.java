package com.luojia.builder.improve;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description 建造者模式 -》产品-> Product
 */
public class House {

    private String baise; // 地基
    private String wall; // 墙体
    private String roofed; // 封顶

    public String getBaise() {
        return baise;
    }

    public void setBaise(String baise) {
        this.baise = baise;
    }

    public String getWall() {
        return wall;
    }

    public void setWall(String wall) {
        this.wall = wall;
    }

    public String getRoofed() {
        return roofed;
    }

    public void setRoofed(String roofed) {
        this.roofed = roofed;
    }
}
