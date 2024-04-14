package at.luojia.spring6.iocxml.di;

public class Dept {

    private String dname;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public void info() {
        System.out.println("部门信息：" + dname);
    }
}
