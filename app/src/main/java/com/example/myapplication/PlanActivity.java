package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landInfoData;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.service.LandService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlanActivity extends AppCompatActivity {
    private int landId;
    private int temperature;
    private int humidity;
    private int Light;
    private int weather;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        landId = i.getIntExtra("landId",0);
        temperature = i.getIntExtra("temperature",0);
        humidity = i.getIntExtra("humidity",0);
        Light = i.getIntExtra("Light",0);
        weather = i.getIntExtra("weather",0);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_plan);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanActivity.this.finish();
            }
        });
        Button plan = findViewById(R.id.plan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlanActivity.this);//设置弹出框的第二种方法
                builder.setTitle("修改");
                builder.setMessage("确定修改吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPlan();
                        PlanActivity.this.finish();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
        Switch water = findViewById(R.id.water);
        Switch CO2 = findViewById(R.id.CO2);
        Switch light = findViewById(R.id.light);
        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    Light++;
                } else {

                }
            }
        });
        CO2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    temperature++;
                } else {

                }
            }
        });
        water.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    humidity++;
                    weather++;
                } else {

                }
            }
        });
    }

    public void requestPlan() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LandService request = retrofit.create(LandService.class);
        Call<landInfoData> call = request.updateInfo(landId,temperature,humidity,Light,weather);
        call.enqueue(new Callback<landInfoData>() {
            @Override
            public void onResponse(Call<landInfoData> call, Response<landInfoData> response) {
                System.out.println("连接成功");
                if(response.body().getStatus()==1){
                    Toast.makeText(PlanActivity.this, "设置成功！！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<landInfoData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
