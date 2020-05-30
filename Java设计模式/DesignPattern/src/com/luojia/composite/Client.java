package com.luojia.composite;

/**
 * @Author Romantic-Lei
 * @Date 2020/5/30
 * @description
 */
public class Client {

    public static void main(String[] args) {
        // 从大到小对象
        OrganizationComponent university = new University("浙江大学", "高等院校");

        // 创建 学院
        OrganizationComponent computerCollege = new College("计算机学院", "计算机学院");
        OrganizationComponent mediumCollege = new College("传媒学院", "传媒学院");

        // 创建专业
        computerCollege.add(new Department("软件工程", "软件工程"));
        computerCollege.add(new Department("网络工程", "网络工程"));
        computerCollege.add(new Department("计算机科学与技术", "计科"));

        mediumCollege.add(new Department("新闻学", "搞新闻的"));

        // 将学院加入到学校
        university.add(computerCollege);
        university.add(mediumCollege);

        university.print();


    }

}
