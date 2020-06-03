package com.example.myapplication.service;

import com.example.myapplication.entity.landlist;
import com.example.myapplication.entity.updateTime;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UpdateService {
    @GET("getLandUpdateTime")
    Call<updateTime> getLandTime();

    @GET("getProductUpdateTime")
    Call<updateTime> getProductTime();
}
