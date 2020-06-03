package com.luojia.iterator;

import java.util.Iterator;
import java.util.List;

/**
 * @Author Romantic-Lei
 * @Date 2020/6/3
 * @description
 */
public class OutPutImpl {

    // 学院集合
    List<College> collegeList;

    public OutPutImpl(List<College> collegeList){
        this.collegeList = collegeList;
    }

    // 遍历所有学院，然后调用printDepartment 输出各个学院的系
    public void printCollege(){
        // 从 collegeList 取出所有学院
        Iterator<College> iterator = collegeList.iterator();

        while (iterator.hasNext()){
            College college = iterator.next();
            System.out.println("===" + college.getName() + "===");
            printDepartment(college.createIterator());
        }
    }

    // 输出系
    public void printDepartment(Iterator iterator){
        while (iterator.hasNext()){
            Department d = (Department) iterator.next();
            System.out.println( "------" + d.getName() + "------");
        }
    }

}
