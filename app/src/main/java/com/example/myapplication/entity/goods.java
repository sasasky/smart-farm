package com.example.myapplication.entity;

public class goods
{
    private String mName;
    private String mPrice;
    private String mPeople;
    private int mNumber;
    private int mDrawable;

    public goods(String mName,String mPrice,String mPeople,int mDrawable)
    {
        this.mName=mName;
        this.mPrice=mPrice;
        this.mPeople=mPeople;
        this.mDrawable=mDrawable;
    }

    public goods(String mName,String mPrice,int mNumber,int mDrawable)
    {
        this.mName=mName;
        this.mPrice=mPrice;
        this.mNumber=mNumber;
        this.mDrawable=mDrawable;
    }


    public String getName()
    {
        return mName;
    }

    public void setName(String mName)
    {
        this.mName = mName;
    }

    public String getPrice()
    {
        return mPrice;
    }

    public void setPrice(String mPrice)
    {
        this.mPrice = mPrice;
    }

    public String getPeople()
    {
        return mPeople;
    }

    public void setPeople(String mPeople)
    {
        this.mPeople = mPeople;
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

}

