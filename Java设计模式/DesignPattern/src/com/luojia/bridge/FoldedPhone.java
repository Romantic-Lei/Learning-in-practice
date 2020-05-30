package com.luojia.bridge;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description
 */
public class FoldedPhone extends Phone {

    public FoldedPhone(Brand brand) {
        super(brand);
    }

    public void open() {
        super.open();
        System.out.println("折叠样式手机开机");
    }

    public void close() {
        super.close();
        System.out.println("折叠样式手机关机");
    }

    public void call() {
        super.call();
        System.out.println("折叠样式手机打电话");
    }

}
