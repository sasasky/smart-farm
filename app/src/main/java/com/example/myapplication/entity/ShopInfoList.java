package com.example.myapplication.entity;

import java.util.List;

public class ShopInfoList {
    private int status;
    private String message;
    private List<ShopInfo> data;

    public ShopInfoList() {
    }

    public ShopInfoList(int status, String message, List<ShopInfo> data) {
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

    public List<ShopInfo> getData() {
        return data;
    }

    public void setData(List<ShopInfo> data) {
        this.data = data;
    }
}
