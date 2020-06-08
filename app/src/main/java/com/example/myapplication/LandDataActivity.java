package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landData;
import com.example.myapplication.entity.landInfo;
import com.example.myapplication.entity.landInfoData;
import com.example.myapplication.service.LandService;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LandDataActivity extends AppCompatActivity {
    private int landId;
    private TextView Land_Name;
    private TextView Land_Locate;
    private ImageView Land_Pic;
    private int temperature;
    private int humidity;
    private int Light;
    private int weather;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        landId = i.getIntExtra("landId",0);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_land_data);
        TextView back =findViewById(R.id.button_backward);
        TextView setting =findViewById(R.id.button_setting);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        setting.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LandDataActivity.this.finish();
            }
        });
        Land_Name =findViewById(R.id.Land_Name);
        Land_Locate =findViewById(R.id.Land_Detail);
        Land_Pic = findViewById(R.id.Land_Pic);
        final TextView water =findViewById(R.id.water);
        final TextView warm =findViewById(R.id.warm);
        final TextView rain =findViewById(R.id.rain);
        final TextView light =findViewById(R.id.light);

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
                Land_Pic.setImageBitmap(lands.getphotoUrl());
            }
            @Override
            public void onFailure(Call<landData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });

        water.setText("65%");
        warm.setText("26℃");
        light.setText("70");
        rain.setText("没有降雨");

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(LandDataActivity.this, PlanActivity.class);//启动MainActivity
                it.putExtra("landId",landId);
                it.putExtra("humidity",humidity);
                it.putExtra("temperature",temperature);
                it.putExtra("Light",Light);
                it.putExtra("weather",weather);
                startActivity(it);
            }
        });
    }
}
