package com.luojia.proxy.dynamic;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/31
 * @description
 */
public class TeacherDao implements ITeacherDao {
    @Override
    public void teach() {
        System.out.println("老师授课中");
    }
}
