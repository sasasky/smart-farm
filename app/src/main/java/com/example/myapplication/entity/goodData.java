package com.example.myapplication.entity;

public class goodData {
    private int status;
    private String message;
    private goods data;

    public goodData() {
    }

    public goodData(int status, String message, goods data) {
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

    public goods getData() {
        return data;
    }

    public void setData(goods data) {
        this.data = data;
    }
}
