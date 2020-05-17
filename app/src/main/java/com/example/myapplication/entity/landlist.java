package com.example.myapplication.entity;


import java.util.List;

public class landlist
{
    private int status;
    private String message;
    private List<land> data;

    public landlist() {
    }

    public landlist(int status, String message, List<land> data) {
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

    public List<land> getData() {
        return data;
    }

    public void setData(List<land> data) {
        this.data = data;
    }
}
