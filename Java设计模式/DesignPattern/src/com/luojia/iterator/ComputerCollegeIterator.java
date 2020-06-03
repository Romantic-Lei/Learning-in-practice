package com.luojia.iterator;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/2
 * @description
 */
public class ComputerCollegeIterator implements Iterator {

    // 这里我们需要Department 是以怎么样的方式存放
    Department[] departments;
    int position = 0; // 遍历的位置

    public ComputerCollegeIterator(Department[] departments){
        this.departments = departments;
    }

    // 判断是否还有下一个元素
    @Override
    public boolean hasNext() {
        if(position >= departments.length || departments[position] == null){
            return false;
        }

        return true;
    }

    @Override
    public Object next() {
        Department department = departments[position];
        position += 1;
        return department;
    }

    // 移除的方法暂时不需要
    @Override
    public void remove() {

    }
}
