package com.example.myapplication.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class User {
    private String name;            //用户名
    private String phone;            //电话
    private String password;        //密码
    private String photo;           //头像
    public enum Id {
        land_owner,land_tenant,consumer;
    };
    private Id identity;

    public User(String name, String phone, String password, String photo, Id identify) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.photo = photo;
        this.identity = identity;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Bitmap getPhoto() {
        Bitmap bitmap = null;
        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        try{
            URL url = new URL(photo);
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
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Id getId() {
        return identity;
    }
    public void setId(Id identify) {
        this.identity = identify;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", photo='" + photo + '\'' +
                ", id='" + identity + '\'' +
                '}';
    }
}