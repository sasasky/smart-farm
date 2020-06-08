package com.example.myapplication.service;

import com.example.myapplication.entity.Putdata;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landData;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.entity.leaseData;
import com.example.myapplication.entity.leaseList;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface LeaseService {
    @POST("leaseLand")
    @FormUrlEncoded
    Call<landData> postResult(
            @Field("uid") String uid,
            @Field("landId") int landId,
            @Field("beginTime") String beginTime,
            @Field("duration") int duration);

    @GET("getLeasedInfo")
    Call<leaseData> getInfo(
            @Query("uid") String userId,
            @Query("landId") int landId,
            @Query("beginTime") String beginTime);

    //根据用户id获取租用土地
    @GET("getLeasedByUid")
    Call<leaseList> getItem(@Query("uid") String userId);

    @PUT("payForLease")
    Call<Putdata> payLand(
            @Query("landId") int landId,
            @Query("uid") String uid,
            @Query("beginTime") String beginTime);

    @PUT("cancelLease")
    Call<Putdata> cancelLand(
            @Query("landId") int landId,
            @Query("uid") String uid,
            @Query("beginTime") String beginTime);
}
