package com.luojia.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description
 */
public class ObjectStructure {

    // 维护了一个集合
    private List<Person> persons = new ArrayList<>();

    // 增加list
    public void attach(Person person) {
        persons.add(person);
    }

    // 移除
    public void datach(Person person) {
        persons.remove(person);
    }

    // 显示测评情况
    public void display(Action action) {
        for (Person person : persons) {
            person.accept(action);
        }
    }

}
