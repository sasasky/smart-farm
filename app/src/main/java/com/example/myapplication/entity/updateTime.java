package com.example.myapplication.entity;

import java.util.Date;

public class updateTime {
    private int status;
    private String message;
    private Date data;

    public updateTime() {
    }

    public updateTime(int status, String message, Date data) {
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
