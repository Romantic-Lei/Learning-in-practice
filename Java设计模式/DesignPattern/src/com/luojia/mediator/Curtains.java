package com.luojia.mediator;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description
 */
public class Curtains extends Colleague {
    public Curtains(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }

    public void UpCurtains() {
        System.out.println("I am holding Up Curtains!");
    }

}
