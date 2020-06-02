package com.luojia.visitor;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description
 */
public class Woman extends Person {
    @Override
    public void accept(Action action) {
        action.getManResult(this);
    }
}
