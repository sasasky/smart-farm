package com.example.myapplication.service;

import com.example.myapplication.entity.GoodPostdata;
import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goods;
import com.example.myapplication.entity.goodslist;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landlist;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface GoodService {
    @GET("getProductInSale")
    Call<goodslist> getItem();

    @GET("getProductByUid")
    Call<goodslist> getItem(@Query("uid") String userId);

    @GET("getProductInfo")
    Call<goodData> getItem(@Query("productId") int productId);

    @POST("addProduct")
    @FormUrlEncoded
    Call<GoodPostdata> postResult(
            @Field("uid") String uid,
            @Field("type") String type,
            @Field("brief") String brief,
            @Field("quantity") double quantity);

    @POST("uploadProductImg")
    @FormUrlEncoded
    Call<GoodPostdata> imgResult(
            @Field("img") String img,
            @Field("productId") int productId);

    @PUT("setProductInSale")
    @FormUrlEncoded
    Call<GoodPostdata> GoodSale(
            @Field("productId") int productId,
            @Field("price") double price);

    @POST("setProductForSelf")
    @FormUrlEncoded
    Call<GoodPostdata> GoodSelf(@Field("productId") int productId);

    @PUT("cancelProductInSale")
    @FormUrlEncoded
    Call<GoodPostdata> CancelSale(
            @Field("productId") int productId);

    @HTTP(method = "DELETE", path = "cancelProductForSelf", hasBody = true)
    Call<GoodPostdata> deleteSelf(
            @Query("productId") int productId);
}
