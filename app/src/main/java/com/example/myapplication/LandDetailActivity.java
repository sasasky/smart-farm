package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.accusation;
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landData;
import com.example.myapplication.service.GoodService;
import com.example.myapplication.service.LandService;
import com.example.myapplication.service.LeaseService;
import com.example.myapplication.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandDetailActivity extends AppCompatActivity {
    private int landId;
    private String userId;
    private String reason;
    private TextView Land_Name;
    private TextView Land_Locate;
    private TextView Land_price;
    private TextView Land_area;
    private ImageView Land_Pic;
    private Button pay;
    private String date;
    String time;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        landId = i.getIntExtra("landId",0);
        userId = i.getStringExtra("userId");
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_land_detail);
        TextView back =findViewById(R.id.button_backward);
        TextView report =findViewById(R.id.button_report);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date(System.currentTimeMillis());
        date = simpleDateFormat.format(today);
        System.out.println(date);
        pay =findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(LandDetailActivity.this);
                new AlertDialog.Builder(LandDetailActivity.this).setTitle("请输入租用天数")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                time = et.getText().toString();
                                if (time.equals("")) {
                                    Toast.makeText(getApplicationContext(), "租用天数不能为空！" + time, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    request();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        report.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LandDetailActivity.this.finish();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(LandDetailActivity.this);
                new AlertDialog.Builder(LandDetailActivity.this).setTitle("请输入举报原因")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                reason = et.getText().toString();
                                if (reason.equals("")) {
                                    Toast.makeText(getApplicationContext(), "举报原因不能为空！" + reason, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    requestReport();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        Land_Name =findViewById(R.id.Land_Name);
        Land_Locate =findViewById(R.id.Land_Detail);
        Land_price =findViewById(R.id.Land_price);
        Land_area =findViewById(R.id.Land_area);
        Land_Pic =findViewById(R.id.Land_Pic);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LandService request = retrofit.create(LandService.class);
        Call<landData> call = request.getItem(landId);
        call.enqueue(new Callback<landData>() {
            @Override
            public void onResponse(Call<landData> call, Response<landData> response) {
                land lands=response.body().getData();
                Land_Name.setText(lands.getlocation());
                Land_Locate.setText(lands.getbrief());
                Land_price.setText(lands.getrent()+"");
                Land_area.setText(lands.getarea()+"㎡");
                Land_Pic.setImageBitmap(lands.getphotoUrl());
            }
            @Override
            public void onFailure(Call<landData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void request() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LeaseService request = retrofit.create(LeaseService.class);
        Call<landData> call = request.postResult(userId,landId,date,Integer.parseInt(time));
        call.enqueue(new Callback<landData>() {
            @Override
            public void onResponse(Call<landData> call, Response<landData> response) {
                System.out.println("连接成功");
                if(response.body().getStatus()==1){
                    Toast.makeText(getApplicationContext(), "租用成功，请尽快支付！", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<landData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestReport() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        UserService request = retrofit.create(UserService.class);
        Call<LandPostdata> call = request.accuseResult(userId,landId, accusation.InfoType.land,reason);
        call.enqueue(new Callback<LandPostdata>() {
            @Override
            public void onResponse(Call<LandPostdata> call, Response<LandPostdata> response) {
                System.out.println("连接成功");
                assert response.body() != null;
                if(response.body().getStatus()==1)
                    Toast.makeText(LandDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
                else System.out.println(response.body().getMsg());
            }
            @Override
            public void onFailure(Call<LandPostdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
