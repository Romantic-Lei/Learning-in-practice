package com.luojia.memento.theory;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description
 */
public class Memento {

    private String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
