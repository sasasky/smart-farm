package com.example.myapplication.entity;

import java.util.List;

public class leaseList {

    private int status;
    private String message;
    private List<lease> data;

    public leaseList() {
    }

    public leaseList(int status, String message, List<lease> data) {
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

    public List<lease> getData() {
        return data;
    }

    public void setData(List<lease> data) {
        this.data = data;
    }
}
