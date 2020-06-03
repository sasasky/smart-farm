package com.example.myapplication.entity;

public class Putdata {
    private int status;
    private String message;

    public Putdata() {
    }

    public Putdata(int status, String message) {
        this.status = status;
        this.message = message;
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
}
