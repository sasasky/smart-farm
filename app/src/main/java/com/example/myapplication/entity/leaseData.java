package com.example.myapplication.entity;

import java.util.List;

public class leaseData {
    private int status;
    private String message;
    private lease data;

    public leaseData() {
    }

    public leaseData(int status, String message, lease data) {
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

    public lease getData() {
        return data;
    }

    public void setData(lease data) {
        this.data = data;
    }
}
