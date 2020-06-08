package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.Putdata;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landData;
import com.example.myapplication.entity.lease;
import com.example.myapplication.entity.leaseData;
import com.example.myapplication.entity.leaseList;
import com.example.myapplication.service.LandService;
import com.example.myapplication.service.LeaseService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayLandActivity extends AppCompatActivity {
    private int landId;
    private String userId;
    private TextView Land_Location;
    private TextView Land_Detail;
    private TextView Land_Area;
    private TextView Begin;
    private TextView Land_Price;
    private TextView Time;
    private ImageView Land_Pic;
    private Button pay;
    private Button cancel;
    private String dateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        landId = i.getIntExtra("landId",0);
        userId = i.getStringExtra("userId");
        dateTime = i.getStringExtra("dateTime");
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_pay_land);
        TextView back =findViewById(R.id.button_backward);
        pay =findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPay();
            }
        });
        cancel =findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCancel();
            }
        });
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayLandActivity.this.finish();
            }
        });
        Land_Location =findViewById(R.id.Land_Location);
        Land_Detail =findViewById(R.id.Land_Detail);
        Land_Area =findViewById(R.id.Land_Area);
        Begin =findViewById(R.id.Begin);
        Land_Price =findViewById(R.id.Land_Price);
        Time =findViewById(R.id.Time);
        Land_Pic =findViewById(R.id.Land_Pic);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LeaseService request = retrofit.create(LeaseService.class);
        Call<leaseData> call = request.getInfo(userId,landId,dateTime);
        call.enqueue(new Callback<leaseData>() {
            @Override
            public void onResponse(Call<leaseData> call, Response<leaseData> response) {
                lease land=response.body().getData();
                Land_Location.setText(land.getlocation());
                Land_Detail.setText(land.getbrief());
                Land_Area.setText(land.getarea()+"㎡");
                Begin.setText(land.getBeginTime());
                Land_Price.setText("￥"+land.getRent());
                Time.setText(land.getDuration()+"天");
                Land_Pic.setImageBitmap(land.getphotoUrl());
            }
            @Override
            public void onFailure(Call<leaseData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestPay() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LeaseService request = retrofit.create(LeaseService.class);
        Call<Putdata> call = request.payLand(landId,userId,dateTime);
        call.enqueue(new Callback<Putdata>() {
            @Override
            public void onResponse(Call<Putdata> call, Response<Putdata> response) {
                System.out.println("连接成功");
                if(response.body().getStatus()==1){
                    Toast.makeText(getApplicationContext(), "支付成功！", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<Putdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestCancel() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LeaseService request = retrofit.create(LeaseService.class);
        Call<Putdata> call = request.cancelLand(landId,userId,dateTime);
        call.enqueue(new Callback<Putdata>() {
            @Override
            public void onResponse(Call<Putdata> call, Response<Putdata> response) {
                System.out.println("连接成功");
                if(response.body().getStatus()==1){
                    Toast.makeText(getApplicationContext(), "租用订单已取消！", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<Putdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
