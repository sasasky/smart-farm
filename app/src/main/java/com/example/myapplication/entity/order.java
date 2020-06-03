package com.example.myapplication.entity;

import java.util.List;

public class order
{
    private int orderId;
    private String userId;
    private String creationTime;
    private double total;
    public enum State {
        unpaid,paid,overdue,cancelled;
    };
    private State state;
    private String name;
    private String phone;
    private String detail;
    private List<goods> productList;
    private double logisticsFee;
    private String logisticsId;

    public order() {
    }

    public order(int orderId, String userId, String creationTime, double total, State state, String name, String phone, String detail, List<goods> productList, double logisticsFee, String logisticsId)
    {
        this.orderId=orderId;
        this.userId=userId;
        this.creationTime=creationTime;
        this.total=total;
        this.state =state;
        this.name=name;
        this.phone=phone;
        this.detail=detail;
        this.productList=productList;
        this.logisticsFee=logisticsFee;
        this.logisticsId=logisticsId;
    }

    public String getTime()
    {
        return creationTime;
    }

    public void setTime(String creationTime)
    {
        this.creationTime = creationTime;
    }

    public int getOrderId()
    {
        return orderId;
    }

    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public State getOrderState() {
        return state;
    }

    public void setOrderState(State state) {
        this.state =state;
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

    public String getAddress()
    {
        return detail;
    }

    public void setAddress(String detail)
    {
        this.detail = detail;
    }

    public List<goods> getProductList()
    {
        return productList;
    }

    public void setProductList( List<goods> productList)
    {
        this.productList = productList;
    }

    public double getLogisticsFee()
    {
        return logisticsFee;
    }

    public void setLogisticsFee(double logisticsFee)
    {
        this.logisticsFee = logisticsFee;
    }

    public String getLogisticsId()
    {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId)
    {
        this.logisticsId = logisticsId;
    }
}

