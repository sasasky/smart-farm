package com.example.myapplication.entity;

public class address
{
    private int addressId;
    private String userId;
    private String name;
    private String phone;
    private String detail;
    private String postcode;

    public address(int addressId,String userId,String name,String phone,String detail,String postcode)
    {
        this.addressId=addressId;
        this.userId=userId;
        this.name=name;
        this.phone=phone;
        this.detail=detail;
        this.postcode=postcode;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public int getAddressId()
    {
        return addressId;
    }

    public void setAddressId(int addressId)
    {
        this.addressId = addressId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getDetail()
    {
        return detail;
    }

    public void setDetail(String detail)
    {
        this.detail = detail;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }
}

