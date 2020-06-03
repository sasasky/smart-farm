package com.example.myapplication.service;

import com.example.myapplication.entity.Putdata;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.entity.accusation;
import com.example.myapplication.entity.adminData;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.entity.reportList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AdminService {
    @GET("getUnexaminedLand")
    Call<landlist> getItem();

    @GET("adminLogin")
    Call<adminData> login(
            @Query("adminId") int adminId,
            @Query("pwd") String pwd);

    @PUT("examineLand")
    Call<Putdata> checkLand(
            @Query("landId") int landId,
            @Query("isPass") boolean isPass);

    @GET("getAccusation")
    Call<reportList> getAccusation(@Query("state") accusation.State state);
    @PUT("handleAccusation")
    Call<Putdata> reportResult(
            @Query("uid") String uid,
            @Query("infoId") int infoId,
            @Query("infoType") accusation.InfoType infoType,
            @Query("adminId") String adminId,
            @Query("state") accusation.State state);
}
