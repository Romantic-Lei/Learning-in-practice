package com.luojia.memento.theory;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description 备忘录模式
 */
public class Originator {

    private String state; // 状态信息

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // 编写一个方法，可以保存一个状态对象Memento
    // 编写一个方法，返回Memento
    public Memento savaStateMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }

}
