package com.luojia.memento.theory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/6
 * @description
 */
public class Caretaker {

    // List 集合中会有很多的备忘录对象
    private List<Memento> mementoList = new ArrayList<Memento>();

    public void add(Memento memento){
        mementoList.add(memento);
    }

    public Memento get(int index){
        return mementoList.get(index);
    }

}
