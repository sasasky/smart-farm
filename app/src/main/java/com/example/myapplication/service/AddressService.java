package com.example.myapplication.service;

import android.database.Observable;

import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.addressData;
import com.example.myapplication.entity.addressList;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AddressService {
    @GET("getAddrByUid")
    Call<addressList> getItem(@Query("uid") String userId);

    @GET("getAddrInfo")
    Call<addressData> getItemInfo(@Query("addrId") int addrId);

    @PUT("updateAddr")
    @FormUrlEncoded
    Call<LandPostdata> postResult(
            @Field("addrId") int addrId,
            @Field("postcode") String postcode,
            @Field("detail") String detail,
            @Field("name") String name,
            @Field("phone") String phone);

    @HTTP(method = "DELETE", path = "deleteAddr", hasBody = true)
    Call<LandPostdata> deleteItem(
            @Query("addrId") int addrId);

    @FormUrlEncoded
    @POST("addAddr")
    Call<LandPostdata> AddResult(
            @Field("uid") String uid,
            @Field("postcode") String postcode,
            @Field("detail") String detail,
            @Field("name") String name,
            @Field("phone") String phone);
}
