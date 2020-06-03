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
    private String ownerId;
    private double area;
    private String location;
    private String photoUrl;
    private String brief;
    public enum lease_State {
        unpaid,paid,cancelled,overdue,expire;
    };
    private lease_State state;
    private Date creationTime;
    private Date expireTime;

    public lease() {
    }

    public lease(int landId, String ownerId, double area, String location, double rent, lease_State state, String photoUrl, String brief, Date creationTime, Date expireTime) {
        this.landId = landId;
        this.ownerId = ownerId;
        this.area = area;
        this.location = location;
        this.state = state;
        this.photoUrl = photoUrl;
        this.creationTime=creationTime;
        this.expireTime=expireTime;
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
