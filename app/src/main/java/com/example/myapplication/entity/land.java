package com.example.myapplication.entity;

import android.net.Uri;

public class land {
    private int landId;
    private String ownerId;
    private double area;
    private String location;
    private double rent;
    private String state;
    private String photoUrl;
    private String brief;

    public land() {
    }

    public land(int landId, String ownerId,double area, String location,double rent, String state,String photoUrl, String brief) {
        this.landId = landId;
        this.ownerId = ownerId;
        this.area = area;
        this.location = location;
        this.rent = rent;
        this.state = state;
        this.photoUrl = photoUrl;
        this.brief = brief;
    }

    public int getlandId() {
        return landId;
    }

    public void setlandId(int landId) {
        this.landId = landId;
    }

    public String getownerId() {
        return ownerId;
    }

    public void setownerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public double getarea() {
        return area;
    }

    public void setarea(double area) {
        this.area = area;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public double getrent() {
        return rent;
    }

    public void setrent(double rent) {
        this.rent = rent;
    }

    public String getstate() {
        return state;
    }

    public void setstate(String state) {
        this.state = state;
    }

    public Uri getphotoUrl() {
        return Uri.parse(photoUrl);
    }

    public void setphotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
    public String getbrief() {
        return brief;
    }

    public void setbrief(String brief) {
        this.brief = brief;
    }
}
