package com.luojia.composite;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description
 */
public class Department extends OrganizationComponent {

    public Department(String name, String des) {
        super(name, des);
    }

    // add, remove 就不用写了，因为他是叶子结点


    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDes() {
        return super.getDes();
    }

    @Override
    protected void print() {
        System.out.println(getName());
    }
}
