package com.example.myapplication.entity;

public class orderSelfData {
    private int status;
    private String message;
    private orderSelf data;

    public orderSelfData() {
    }

    public orderSelfData(int status, String message, orderSelf data) {
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

    public orderSelf getData() {
        return data;
    }

    public void setData(orderSelf data) {
        this.data = data;
    }
}
