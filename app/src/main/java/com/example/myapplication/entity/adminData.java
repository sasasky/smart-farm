package com.example.myapplication.entity;

public class adminData {
    private int status;
    private String message;
    private admin data;

    public adminData() {
    }

    public adminData(int status, String message, admin data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String message) {
        this.message = message;
    }

    public admin getData() {
        return data;
    }

    public void setData(admin data) {
        this.data = data;
    }
}
