package com.example.myapplication.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class land {
    private int landId;
    private String ownerId;
    private double area;
    private String location;
    private double rent;
    private String photoUrl;
    private String brief;
    public enum land_State {
        unexamined,pass,leased,unqualified,banned,cancelled;
    };
    private land_State state;

    public land() {
    }

    public land(int landId, String ownerId,double area, String location,double rent, land_State state,String photoUrl, String brief) {
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

    public land_State getstate() {
        return state;
    }

    public void setstate(land_State state) {
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
}
