package com.example.myapplication.entity;

import java.util.List;

public class reportList
{
    private int status;
    private String message;
    private List<accusation> data;

    public reportList() {
    }

    public reportList(int status, String message, List<accusation> data) {
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

    public List<accusation> getData() {
        return data;
    }

    public void setData(List<accusation> data) {
        this.data = data;
    }
}
