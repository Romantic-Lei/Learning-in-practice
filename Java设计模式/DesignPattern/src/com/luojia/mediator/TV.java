package com.luojia.mediator;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description
 */
public class TV extends Colleague {
    public TV(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }

    public void StartTv() {
        System.out.println("It's time to StartTv!");
    }


    public void StopTv() {
        System.out.println("StopTv!");
    }


}
