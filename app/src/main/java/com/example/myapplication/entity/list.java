package com.example.myapplication.entity;

public class list
{
    private String mTime;
    private String mState;
    private String mName;
    private String mPrice;
    private String mNum;
    private String mSum;
    private int mDrawable;

    public list(String mTime,String mState,String mName,String mPrice,String mNum,String mSum,int mDrawable)
    {
        this.mTime=mTime;
        this.mState=mState;
        this.mName=mName;
        this.mPrice=mPrice;
        this.mNum=mNum;
        this.mSum=mSum;
        this.mDrawable=mDrawable;
    }

    public String getTime()
    {
        return mTime;
    }

    public void setTime(String mTime)
    {
        this.mTime = mTime;
    }

    public String getState()
    {
        return mState;
    }

    public void setState(String mState)
    {
        this.mState = mState;
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

    public String getNum()
    {
        return mNum;
    }

    public void setNum(String mNum)
    {
        this.mNum = mNum;
    }

    public String getSum()
    {
        return mSum;
    }

    public void setSum(String mSum)
    {
        this.mSum = mSum;
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

