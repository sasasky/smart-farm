package com.example.myapplication.service;

import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.ShopInfo;
import com.example.myapplication.entity.ShopInfoList;
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goodslist;
import com.example.myapplication.entity.orderlist;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartService {
    @FormUrlEncoded
    @POST("addToTrolley")
    Call<goodData> postResult(
            @Field("uid") String uid,
            @Field("productId") int productId);

    @GET("getTrolleyByUid")
    Call<ShopInfoList> getItem(@Query("uid") String userId);

    @HTTP(method = "DELETE", path = "deleteFromTrolley", hasBody = true)
    Call<ShopInfo> deleteItem(
            @Query("uid") String uid,
            @Query("productId") int productId);
}
