package com.luojia.absFactory.pizza;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/28
 * @description
 */
public abstract class  Pizza {

    protected String name; // 名字

    public abstract void prepare();

    public void bake(){
        System.out.println(name + "baking");
    }

    public void cut(){
        System.out.println(name + "cutting");
    }

    public void box(){
        System.out.println(name + "boxing");
    }

    public void setName(String name) {
        this.name = name;
    }


}
