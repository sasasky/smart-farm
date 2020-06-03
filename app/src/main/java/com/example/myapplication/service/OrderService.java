package com.example.myapplication.service;

import com.example.myapplication.entity.Putdata;
import com.example.myapplication.entity.ShopInfo;
import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.entity.goodslist;
import com.example.myapplication.entity.landData;
import com.example.myapplication.entity.orderData;
import com.example.myapplication.entity.orderSelfData;
import com.example.myapplication.entity.orderlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface OrderService {
    @GET("getOrderByUid")
    Call<orderlist> getItem(@Query("uid") String userId);

    @GET("getOrderForSelfByUid")
    Call<orderlist> getSelf(@Query("uid") String userId);

    @GET("getOrderInfo")
    Call<orderData> getItem(@Query("orderId") int orderId);

    @GET("getOrderForSelfInfo")
    Call<orderSelfData> getSelfInfo(@Query("orderId") int orderId);

    @PUT("cancelOrder")
    Call<Putdata> cancelOrder(
            @Query("orderId") int orderId);

    @PUT("payForOrder")
    Call<Putdata> payForOrder(
            @Query("orderId") int orderId);

    @PUT("payForOrderForSelf")
    Call<Putdata> payForSelfOrder(
            @Query("orderId") int orderId);

    @HTTP(method = "DELETE", path = "cancelOrderForSelf", hasBody = true)
    Call<Putdata> deleteItem(
            @Query("orderId") int orderId);

    @POST("placeOrder")
    @FormUrlEncoded
    Call<orderData> placeOrder(
            @Field("uid") String uid,
            @Field("pIdList") List<Integer> pIdList,
            @Field("postcode") String postcode,
            @Field("phone") String phone,
            @Field("name") String name,
            @Field("detail") String detail);

    @POST("placeOrderForSelf")
    @FormUrlEncoded
    Call<orderData> placeSelfOrder(
            @Field("uid") String uid,
            @Field("postcode") String postcode,
            @Field("detail") String detail,
            @Field("name") String name,
            @Field("phone") String phone);
}
