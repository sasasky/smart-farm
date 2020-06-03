package com.example.myapplication.entity;

public class landInfoData {
    private int status;
    private String message;
    private landInfo data;

    public landInfoData() {
    }

    public landInfoData(int status, String message, landInfo data) {
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

    public landInfo getData() {
        return data;
    }

    public void setData(landInfo data) {
        this.data = data;
    }
}
