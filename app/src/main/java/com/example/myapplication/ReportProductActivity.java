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
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goods;
import com.example.myapplication.entity.reportList;
import com.example.myapplication.service.AdminService;
import com.example.myapplication.service.GoodService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReportProductActivity extends AppCompatActivity {
    private int goodId;
    private String userId;
    private String Reason;
    private TextView Good_name;
    private TextView Good_detail;
    private TextView Good_price;
    private TextView Good_num;
    private TextView reason;
    private ImageView Good_Pic;
    private Button check;
    private accusation.State state;
    private AlertDialog alertDialog;
    final private String[] items = {"同意举报", "驳回举报"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        goodId = i.getIntExtra("InfoId",0);
        userId = i.getStringExtra("uid");
        Reason = i.getStringExtra("reason");
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_report_product);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ReportProductActivity.this, AdminMainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        Good_name =findViewById(R.id.Good_name);
        Good_detail =findViewById(R.id.Good_detail);
        Good_price =findViewById(R.id.Good_price);
        Good_num =findViewById(R.id.Good_num);
        Good_Pic =findViewById(R.id.Good_Pic);
        reason =findViewById(R.id.Reason);
        reason.setText(Reason);
        reason.setText(Reason);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        final GoodService request = retrofit.create(GoodService.class);
        Call<goodData> call = request.getItem(goodId);
        call.enqueue(new Callback<goodData>() {
            @Override
            public void onResponse(Call<goodData> call, Response<goodData> response) {
                goods good=response.body().getData();
                Good_name.setText(good.gettype());
                Good_price.setText(good.getprice()+"");
                Good_detail.setText(good.getbrief());
                Good_num.setText(good.getquantity()+"");
                Good_Pic.setImageBitmap(good.getphotoUrl());
            }
            @Override
            public void onFailure(Call<goodData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });

        check =findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ReportProductActivity.this);
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
                        requestReportProduct();
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

    public void requestReportProduct() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        AdminService request = retrofit.create(AdminService.class);
        Call<Putdata> call = request.reportResult(userId,goodId,accusation.InfoType.product,"10000", accusation.State.unhandled);
        call.enqueue(new Callback<Putdata>() {
            @Override
            public void onResponse(Call<Putdata> call, Response<Putdata> response) {
                System.out.println("连接成功");
                if(response.body() != null){
                    Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                }
                Intent it=new Intent(ReportProductActivity.this, AdminMainActivity.class);//启动MainActivity
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
