package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landData;
import com.example.myapplication.service.LandService;
import com.example.myapplication.service.LeaseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyLandActivity extends AppCompatActivity {
    private int landId;
    private TextView Land_Name;
    private TextView Land_Locate;
    private TextView Land_price;
    private TextView Land_area;
    private ImageView Land_Pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        landId = i.getIntExtra("landId",0);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_my_land);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyLandActivity.this.finish();
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
}
