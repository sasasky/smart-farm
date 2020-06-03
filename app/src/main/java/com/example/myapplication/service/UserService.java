package com.example.myapplication.service;

import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.entity.accusation;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {
    //登录
    @GET("login")
    Call<UserData> getUser(
            @Query("uid") String userId,
            @Query("pwd") String password);

    //注册
    @POST("register")
    @FormUrlEncoded
    Call<UserData> postResult(
            @Field("uid") String uid,
            @Field("pwd") String password,
            @Field("identity") User.Id id);

    @GET("getUserInfo")
    Call<UserData> getInfo(
            @Query("uid") String userId);

    @PUT("updatePwd")
    Call<UserData> changepwd(
            @Query("uid") String uid,
            @Query("oldPwd") String oldPwd,
            @Query("newPwd") String newPwd);

    @PUT("updateInfo")
    Call<UserData> changeInfo(
            @Query("uid") String uid,
            @Query("name") String name);

    @PUT("uploadUserImg")
    Call<UserData> changePic(
            @Query("uid") String uid,
            @Query("img") String img);

    @POST("accuse")
    @FormUrlEncoded
    Call<LandPostdata> accuseResult(
            @Field("uid") String uid,
            @Field("infoId") int infoId,
            @Field("infoType") accusation.InfoType infoType,
            @Field("reason") String reason);
}
