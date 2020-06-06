package com.luojia.mediator;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 创建一个中介者对象
        ConcreateMediator concreateMediator = new ConcreateMediator();

        // 创建Alarm 并且加入到 concreateMediator 对象的HashMap
        Alarm alarm = new Alarm(concreateMediator, "alarm");

        CoffeeMachine coffeeMachine = new CoffeeMachine(concreateMediator, "coffeeMachine");

        Curtains curtains = new Curtains(concreateMediator, "curtains");
        TV tv = new TV(concreateMediator, "TV");

        alarm.sendAlarm(0);
        coffeeMachine.finishCoffee();
        alarm.sendAlarm(1);

    }

}
