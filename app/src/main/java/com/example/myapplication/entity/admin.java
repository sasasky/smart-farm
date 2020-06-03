package com.example.myapplication.entity;

public class admin {
    private int adminId;            //电话
    private String pwd;        //密码

    public admin(int adminId, String pwd) {
        this.adminId = adminId;
        this.pwd = pwd;
    }

    public int getAdminId() {
        return adminId;
    }
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
