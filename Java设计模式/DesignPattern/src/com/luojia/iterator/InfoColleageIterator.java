package com.luojia.iterator;

import java.util.Iterator;
import java.util.List;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/3
 * @description
 */
public class InfoColleageIterator implements Iterator {

    List<Department> departmentList;// 信息工程学院以List方式存放
    int index = -1; // 索引

    public InfoColleageIterator(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    @Override
    public boolean hasNext() {
        if(index >= departmentList.size() - 1){
            return false;
        }

        index += 1;
        return true;
    }

    @Override
    public Object next() {
        return departmentList.get(index);
    }

    // 空实现
    @Override
    public void remove() {

    }
}
