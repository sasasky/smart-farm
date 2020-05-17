package com.example.myapplication.entity;

public class User {
    private String name;            //用户名
    private String phone;            //电话
    private String password;        //密码
    private String photo;           //头像
    private int id;                 //用户身份

    public User(String name, String phone, String password, String photo, int id) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.photo = photo;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}