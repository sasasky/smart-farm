package com.example.myapplication.entity;

import java.util.List;

public class UserData {
    private int status;
    private String message;
    private User data;

    public UserData() {
    }

    public UserData(int status, String message, User data) {
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

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
