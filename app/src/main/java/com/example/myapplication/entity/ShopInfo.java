package com.example.myapplication.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShopInfo
{
    public int productId;
    public String type;
    public double price;
    public double quantity;
    public String photoUrl;
    public boolean isCheck;

    public ShopInfo(int productId, String type, double price, double quantity, String photoUrl, boolean isCheck)
    {
        this.productId=productId;
        this.type=type;
        this.price=price;
        this.quantity=quantity;
        this.photoUrl=photoUrl;
        this.isCheck=isCheck;
    }

    public ShopInfo() {
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getName()
    {
        return type;
    }

    public void setName(String type)
    {
        this.type = type;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getNumber()
    {
        return quantity;
    }

    public void setNumber(double quantity)
    {
        this.quantity = quantity;
    }

    public Bitmap getDrawable()
    {
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

    public void setDrawable(String photoUrl)
    {
        this.photoUrl = photoUrl;
    }

    public boolean getCheck()
    {
        return isCheck;
    }

    public void setCheck(boolean isCheck)
    {
        this.isCheck = isCheck;
    }

}

