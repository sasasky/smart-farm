package com.example.myapplication.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class lease {
    private int landId;
    private String hirerId;
    private double area;
    private double rent;
    private String location;
    private String photoUrl;
    private String brief;
    private int duration;
    private String beginTime;
    public enum lease_State {
        unpaid,paid,cancelled,overdue,expire;
    };
    private lease_State state;
    private Date creationTime;
    private Date expireTime;

    public lease() {
    }

    public lease(int landId, int duration, String hirerId, double area, String location, String beginTime, double rent, lease_State state, String photoUrl, String brief, Date creationTime, Date expireTime) {
        this.landId = landId;
        this.hirerId = hirerId;
        this.area = area;
        this.location = location;
        this.state = state;
        this.rent = rent;
        this.photoUrl = photoUrl;
        this.creationTime=creationTime;
        this.expireTime=expireTime;
        this.brief = brief;
        this.beginTime = beginTime;
        this.duration = duration;
    }

    public int getlandId() {
        return landId;
    }

    public void setlandId(int landId) {
        this.landId = landId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getownerId() {
        return hirerId;
    }

    public void setownerId(String hirerId) {
        this.hirerId = hirerId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public double getarea() {
        return area;
    }

    public void setarea(double area) {
        this.area = area;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        this.rent = rent;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public lease_State getstate() {
        return state;
    }

    public void setstate(lease_State state) {
        this.state = state;
    }

    public Bitmap getphotoUrl(){
        Bitmap bitmap = null;
        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        try{
            URL url = new URL(photoUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                InputStream inputStream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
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

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
