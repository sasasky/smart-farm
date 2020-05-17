package com.example.myapplication.entity;

public class ShopInfo
{
    public String mName;
    public int mPrice;
    public int mNumber;
    public int mDrawable;
    public boolean isCheck;

    public ShopInfo(String mName, int mPrice, int mNumber, int mDrawable, boolean isCheck)
    {
        this.mName=mName;
        this.mPrice=mPrice;
        this.mNumber=mNumber;
        this.mDrawable=mDrawable;
        this.isCheck=isCheck;
    }

    public ShopInfo() {
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String mName)
    {
        this.mName = mName;
    }

    public int getPrice()
    {
        return mPrice;
    }

    public void setPrice(int mPrice)
    {
        this.mPrice = mPrice;
    }

    public int getNumber()
    {
        return mNumber;
    }

    public void setNumber(int mNumber)
    {
        this.mNumber = mNumber;
    }

    public int getDrawable()
    {
        return mDrawable;
    }

    public void setDrawable(int mDrawable)
    {
        this.mDrawable = mDrawable;
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

