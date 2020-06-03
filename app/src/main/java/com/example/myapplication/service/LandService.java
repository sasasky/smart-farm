package com.example.myapplication.service;

import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landData;
import com.example.myapplication.entity.landInfoData;
import com.example.myapplication.entity.landlist;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LandService {
    //获取所有可租用土地
    @GET("getPassLand")
    Call<landlist> getItem();

    //根据用户id获取发布土地
    @GET("getLandByUid")
    Call<landlist> getItem(@Query("uid") String userId);

    //获取土地详情
    @GET("getLandInfo")
    Call<landData> getItem(@Query("landId") int landId);

    //取消发布土地
    @PUT("cancelLand/{landId}")
    Call<landlist> putInfo(@Path("landId") int landId);

    //发布土地
    @POST("addLand")
    @FormUrlEncoded
    Call<LandPostdata> postResult(
            @Field("uid") String uid,
            @Field("location") String location,
            @Field("rent") double rent,
            @Field("brief") String brief,
            @Field("area") double area);

    @POST("uploadLandImg")
    @FormUrlEncoded
    Call<LandPostdata> imgResult(
            @Field("img") String img,
            @Field("landId") int landId);

    @GET("getFarmInfo")
    Call<landInfoData> getInfo(@Query("landId") int landId);

    @PUT("updateFarmInfo")
    @FormUrlEncoded
    Call<landInfoData> updateInfo(
            @Field("landId") int landId,
            @Field("temperature") int temperature,
            @Field("humidity") int humidity,
            @Field("light") int light,
            @Field("weather") int weather);
}
