package com.luojia.mediator;

import java.util.HashMap;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/4
 * @description 具体的中介者类
 */
public class ConcreateMediator extends Mediator {

    private HashMap<String, Colleague> colleagueMap;
    private HashMap<String, String> interMap;

    public ConcreateMediator() {
        colleagueMap = new HashMap<String, Colleague>();
        interMap = new HashMap<String, String>();
    }

    @Override
    public void register(String colleagueName, Colleague colleague) {
        colleagueMap.put(colleagueName, colleague);

        if (colleague instanceof Alarm) {
            interMap.put("Alarm", colleagueName);
        } else if (colleague instanceof CoffeeMachine) {
            interMap.put("CoffeeMachine", colleagueName);
        } else if (colleague instanceof TV) {
            interMap.put("TV", colleagueName);
        } else if (colleague instanceof Curtains) {
            interMap.put("Curtains", colleagueName);
        }
    }

    @Override
    public void getMessage(int stateChange, String colleagueName) {
        //处理闹钟发出的消息
        if (colleagueMap.get(colleagueName) instanceof Alarm) {
            if (stateChange == 0) {
                ((CoffeeMachine) (colleagueMap.get(interMap
                        .get("CoffeeMachine")))).startCoffee();
                ((TV) (colleagueMap.get(interMap.get("TV")))).StartTv();
            } else if (stateChange == 1) {
                ((TV) (colleagueMap.get(interMap.get("TV")))).StopTv();
            }


        } else if (colleagueMap.get(colleagueName) instanceof CoffeeMachine) {
            ((Curtains) (colleagueMap.get(interMap.get("Curtains"))))
                    .UpCurtains();

        } else if (colleagueMap.get(colleagueName) instanceof TV) {//如果 TV 发现消息


        } else if (colleagueMap.get(colleagueName) instanceof Curtains) {
            //如果是以窗帘发出的消息，这里处理...
        }
    }

    @Override
    public void sendMessage() {

    }
}
