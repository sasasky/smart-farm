package com.example.myapplication.entity;

public class orderData {
    private int status;
    private String message;
    private order data;

    public orderData() {
    }

    public orderData(int status, String message, order data) {
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

    public order getData() {
        return data;
    }

    public void setData(order data) {
        this.data = data;
    }
}
