package com.luojia.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description
 */
public class College extends OrganizationComponent {

    List<OrganizationComponent> organizationComponents = new ArrayList<OrganizationComponent>();

    public College(String name, String des) {
        super(name, des);
    }

    // 重写add
    @Override
    protected void add(OrganizationComponent organizationComponent) {
        organizationComponents.add(organizationComponent);
    }

    // 重写remove
    @Override
    protected void remove(OrganizationComponent organizationComponent) {
        organizationComponents.remove(organizationComponent);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDes() {
        return super.getDes();
    }

    // print方法，就是输出University 包含的学院
    @Override
    protected void print() {
        System.out.println("-----------------" + getName() +  "---------------");
        // 遍历
        for (OrganizationComponent organizationComponent: organizationComponents) {
            organizationComponent.print();
        }
    }
}
