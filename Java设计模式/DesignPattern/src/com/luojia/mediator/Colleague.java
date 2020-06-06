package com.luojia.mediator;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description 同事抽象类
 */
public abstract class Colleague {

    private Mediator mediator;
    public String name;

    public Colleague(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public Mediator getMediator() {
        return mediator;
    }

    public abstract void sendMessage(int stateChange);
}
