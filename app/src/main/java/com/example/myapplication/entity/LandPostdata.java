package com.example.myapplication.entity;

public class LandPostdata {
    private int status;
    private String message;
    private int landId;

    public LandPostdata() {
    }

    public LandPostdata(int status, String message) {
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

    public int getLandId() {
        return landId;
    }

    public void setLandId(int landId) {
        this.landId = landId;
    }
}