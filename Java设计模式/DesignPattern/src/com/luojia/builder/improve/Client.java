package com.luojia.builder.improve;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/29
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 盖普通房子
        CommonHouse commonHouse = new CommonHouse();

        // 准备创建房子的指挥者
        HouseDirector houseDirector = new HouseDirector(commonHouse);

        // 完成盖房子，返回产品（房子）
        House house = houseDirector.constructHouse();

        System.out.println("------------------------------------------");

        // 盖高楼
        HightBuilding hightBuilding = new HightBuilding();
        //    重置建造者
        houseDirector.setHouseBuilder(hightBuilding);
        // 完成盖房子，返回产品（高楼）
        houseDirector.constructHouse();


    }
}
