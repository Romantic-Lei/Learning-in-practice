package com.luojia.visitor;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description
 */
public abstract class Action {

    // 得到男性的测评
    public abstract void getManResult(Man man);

    // 得到女性的测评
    public abstract void getManResult(Woman woman);

}
