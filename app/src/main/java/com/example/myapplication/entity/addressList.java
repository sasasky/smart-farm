package com.example.myapplication.entity;

import java.util.List;

public class addressList
{
    private int status;
    private String message;
    private List<address> data;

    public addressList() {
    }

    public addressList(int status, String message, List<address> data) {
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

    public List<address> getData() {
        return data;
    }

    public void setData(List<address> data) {
        this.data = data;
    }
}

