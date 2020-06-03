package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.Putdata;
import com.example.myapplication.entity.accusation;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landData;
import com.example.myapplication.entity.reportList;
import com.example.myapplication.service.AdminService;
import com.example.myapplication.service.LandService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportLandActivity extends AppCompatActivity {
    private int landId;
    private String userId;
    private String Reason;
    private TextView Land_Name;
    private TextView Land_Locate;
    private TextView Land_price;
    private TextView Land_area;
    private TextView reason;
    private ImageView Land_Pic;
    private Button check;
    private accusation.State state;
    private AlertDialog alertDialog;
    final private String[] items = {"同意举报", "驳回举报"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        landId = i.getIntExtra("InfoId",0);
        userId = i.getStringExtra("uid");
        Reason = i.getStringExtra("reason");
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_report_land);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ReportLandActivity.this, AdminMainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        Land_Name =findViewById(R.id.Land_Location);
        Land_Locate =findViewById(R.id.Land_Detail);
        Land_price =findViewById(R.id.Land_Price);
        Land_area =findViewById(R.id.Land_Area);
        Land_Pic =findViewById(R.id.Land_Pic);
        reason =findViewById(R.id.Reason);
        reason.setText(Reason);
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

        check =findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ReportLandActivity.this);
                alertBuilder.setTitle("请选择处理结果");
                alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0){
                            state = accusation.State.consent;
                        } else if(i==1){
                            state = accusation.State.rejected;
                        }
                    }
                });
                alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestReportLand();
                    }
                });
                alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog = alertBuilder.create();
                alertDialog.show();
            }
        });
    }

    public void requestReportLand() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        AdminService request = retrofit.create(AdminService.class);
        Call<Putdata> call = request.reportResult(userId,landId,accusation.InfoType.land,"10000",accusation.State.unhandled);
        call.enqueue(new Callback<Putdata>() {
            @Override
            public void onResponse(Call<Putdata> call, Response<Putdata> response) {
                System.out.println("连接成功");
                if(response.body() != null){
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
                Intent it=new Intent(ReportLandActivity.this, AdminMainActivity.class);//启动MainActivity
                startActivity(it);
                System.out.println(response.message()+"   "+response.body());
            }
            @Override
            public void onFailure(Call<Putdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
