package com.luojia.mediator;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description 具体的同事类
 */
public class Alarm extends Colleague {

    public Alarm(Mediator mediator, String name) {
        super(mediator, name);
        // 在创建Alarm 同事对象时，将自己放入到ConcreateMediator对象中【集合】
        mediator.register(name, this);
    }

    public void sendAlarm(int stateChange){
        sendMessage(stateChange);
    }

    @Override
    public void sendMessage(int stateChange) {
        // 调用中介者模式
        this.getMediator().getMessage(stateChange, this.name);
    }
}
