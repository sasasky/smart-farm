package com.example.myapplication.entity;

public class addressData
{
    private int status;
    private String message;
    private address data;

    public addressData() {
    }

    public addressData(int status, String message, address data) {
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

    public address getData() {
        return data;
    }

    public void setData(address data) {
        this.data = data;
    }
}
