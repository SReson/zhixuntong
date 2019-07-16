package com.example.leoss.zhuye.ParseXls;

public class Teacher_info
{
    String name;
    String sex;
    String address;
    String major;
    String phone;
    String qq;
    String weixin;

    public Teacher_info(String name, String sex, String address, String major, String phone, String qq, String weixin)
    {
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.major = major;
        this.phone = phone;
        this.qq = qq;
        this.weixin = weixin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    @Override
    public String toString() {
        return "Teacher_info{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", major='" + major + '\'' +
                ", phone='" + phone + '\'' +
                ", qq='" + qq + '\'' +
                ", weixin='" + weixin + '\'' +
                '}';
    }
}
