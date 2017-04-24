package com.zp.cn.myapplication.model;

/**
 * Created by Administrator on 2017/4/24.
 */

public class Student {

    private String imgUrl;
    private String tel;
    private String salary;

    public Student(String imgUrl, String tel, String salary) {
        this.imgUrl = imgUrl;
        this.tel = tel;
        this.salary = salary;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
