package com.example.myapplication.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class goods
{
    private int productId;
    private String ownerId;
    private double quantity;
    private String type;
    private double price;
    public enum Good_State {
        unsold,for_self,in_sale,sold_out,banned;
    };
    private Good_State state;
    private String photoUrl;
    private String brief;

    public goods(int productId, String ownerId,double quantity, String type,double price, Good_State state,String photoUrl, String brief) {
        this.productId = productId;
        this.ownerId = ownerId;
        this.quantity = quantity;
        this.type = type;
        this.price = price;
        this.state = state;
        this.photoUrl = photoUrl;
        this.brief = brief;
    }

    public int getproductId() {
        return productId;
    }

    public void setproductId(int productId) {
        this.productId = productId;
    }

    public String getownerId() {
        return ownerId;
    }

    public void setownerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public double getquantity() {
        return quantity;
    }

    public void setquantity(double quantity) {
        this.quantity = quantity;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    public double getprice() {
        return price;
    }

    public void setprice(double price) {
        this.price = price;
    }

    public Good_State getGoodState() {
        return state;
    }

    public void setGoodState(Good_State state) {
        this.state = state;
    }

    public Bitmap getphotoUrl() {
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

