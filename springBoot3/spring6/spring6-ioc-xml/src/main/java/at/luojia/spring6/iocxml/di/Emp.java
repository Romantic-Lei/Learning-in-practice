package at.luojia.spring6.iocxml.di;

import java.util.Arrays;

public class Emp {

    private String ename;
    private String age;
    // 爱好
    private String[] hobbies;
    private Dept dept;

    public void work() {
        System.out.println(ename + "emp working ... " + age);
        dept.info();
        System.out.println("爱好： " + (hobbies == null ? " " : hobbies));
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "ename='" + ename + '\'' +
                ", age='" + age + '\'' +
                ", hobbies=" + Arrays.toString(hobbies) +
                ", dept=" + dept +
                '}';
    }
}
