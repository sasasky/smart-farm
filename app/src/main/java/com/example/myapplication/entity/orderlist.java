package com.example.myapplication.entity;

import java.util.List;

public class orderlist
{
    private int status;
    private String message;
    private List<order> data;

    public orderlist() {
    }

    public orderlist(int status, String message, List<order> data) {
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

    public List<order> getData() {
        return data;
    }

    public void setData(List<order> data) {
        this.data = data;
    }
}
