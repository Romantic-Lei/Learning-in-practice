package com.luojia.redis7.entities;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_customer")
public class Customer {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String cname;

    private Integer age;

    private String phone;

    private Byte ex;

    private Date birth;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return cname
     */
    public String getCname() {
        return cname;
    }

    /**
     * @param cname
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return ex
     */
    public Byte getEx() {
        return ex;
    }

    /**
     * @param ex
     */
    public void setEx(Byte ex) {
        this.ex = ex;
    }

    /**
     * @return birth
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * @param birth
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }
}