package com.example.myapplication.entity;

public class landData {
    private int status;
    private String message;
    private land data;

    public landData() {
    }

    public landData(int status, String message, land data) {
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

    public land getData() {
        return data;
    }

    public void setData(land data) {
        this.data = data;
    }
}
