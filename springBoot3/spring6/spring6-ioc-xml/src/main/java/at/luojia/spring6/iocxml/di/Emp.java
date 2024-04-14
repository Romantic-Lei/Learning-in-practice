package at.luojia.spring6.iocxml.di;

public class Emp {

    private String ename;
    private String age;
    private Dept dept;

    public void work() {
        System.out.println(ename + "emp working ... " + age);
        dept.info();
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

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }
}
