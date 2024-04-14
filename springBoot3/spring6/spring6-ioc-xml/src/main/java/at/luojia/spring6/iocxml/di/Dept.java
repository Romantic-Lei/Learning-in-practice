package at.luojia.spring6.iocxml.di;

import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class Dept {

    private String dname;
    private List<Emp> empList;

    private Map<String, Emp> empMap;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public List<Emp> getEmpList() {
        return empList;
    }

    public void setEmpList(List<Emp> empList) {
        this.empList = empList;
    }

    public Map<String, Emp> getEmpMap() {
        return empMap;
    }

    public void setEmpMap(Map<String, Emp> empMap) {
        this.empMap = empMap;
    }

    public void info() {
        System.out.println("部门信息：" + dname);
        if (!CollectionUtils.isEmpty(empList)) {
            for (Emp emp : empList) {
                System.out.println(emp.getEname());
            }
        }

        if (!CollectionUtils.isEmpty(empMap)) {
            System.out.println(empMap);
        }
    }
}
