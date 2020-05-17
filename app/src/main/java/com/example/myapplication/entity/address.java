package com.example.myapplication.entity;

public class address
{
    private String mName;
    private String mPhone;
    private String mAddress;

    public address(String mName,String mPhone,String mAddress)
    {
        this.mName=mName;
        this.mPhone=mPhone;
        this.mAddress=mAddress;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String mName)
    {
        this.mName = mName;
    }

    public String getPhone()
    {
        return mPhone;
    }

    public void setPhone(String mPhone)
    {
        this.mPhone = mPhone;
    }

    public String getAddress()
    {
        return mAddress;
    }

    public void setAddress(String mAddress)
    {
        this.mAddress = mAddress;
    }

}

