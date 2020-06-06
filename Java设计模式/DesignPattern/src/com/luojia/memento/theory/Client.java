package com.luojia.memento.theory;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description
 */
public class Client {

    public static void main(String[] args) {
        Originator originator = new Originator();
        Caretaker caretaker = new Caretaker();

        originator.setState("状态1");
        caretaker.add(originator.savaStateMemento());

        originator.setState("状态2");
        caretaker.add(originator.savaStateMemento());

        originator.setState("状态3");
        caretaker.add(originator.savaStateMemento());

        System.out.println("当前的状态是： " + originator.getState());

        // 希望恢复到状态1
        originator.getStateFromMemento(caretaker.get(0));
        System.out.println(originator.getState());

    }

}
