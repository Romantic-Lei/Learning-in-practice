package com.luojia.iterator;

import java.util.Iterator;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/3
 * @description
 */
public interface College {

    public String getName();

    // 增加系的方法
    public void addDepartment(String name, String desc);

    // 返回一个迭代器
    public Iterator createIterator();

}
