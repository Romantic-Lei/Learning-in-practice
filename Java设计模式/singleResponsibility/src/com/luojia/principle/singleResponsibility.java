package com.luojia.principle;

public class singleResponsibility {
    public static void main(String[] args) {
        RoadVehicle roadVehicle = new RoadVehicle();
        roadVehicle.run("汽车");
        roadVehicle.runAir("飞机");
    }
}

class RoadVehicle{
    public void run(String vehicle){
        System.out.println(vehicle + "公路运输");
    }

    public void runAir(String vehicle){
        System.out.println(vehicle + "空中运输");
    }
}
