package com.example.myapplication.entity;

import java.util.List;

public class goodslist
{
    private int status;
    private String message;
    private List<goods> data;

    public goodslist() {
    }

    public goodslist(int status, String message, List<goods> data) {
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

    public List<goods> getData() {
        return data;
    }

    public void setData(List<goods> data) {
        this.data = data;
    }
}