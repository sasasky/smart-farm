package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.myapplication.entity.GoodPostdata;
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goods;
import com.example.myapplication.entity.landData;
import com.example.myapplication.service.GoodService;
import com.example.myapplication.service.LeaseService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyGoodActivity extends AppCompatActivity {
    private int productId;
    private String price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        productId = i.getIntExtra("productId",0);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_my_good);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyGoodActivity.this.finish();
            }
        });
        final ImageView good_pic =findViewById(R.id.good_pic);
        final TextView Good_Name =findViewById(R.id.Good_Name);
        final TextView Good_Num =findViewById(R.id.Good_Num);
        final TextView Good_Detail =findViewById(R.id.Good_Detail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        GoodService request = retrofit.create(GoodService.class);
        Call<goodData> call = request.getItem(productId);
        call.enqueue(new Callback<goodData>() {
            @Override
            public void onResponse(Call<goodData> call, Response<goodData> response) {
                goods good=response.body().getData();
                Good_Name.setText(good.gettype());
                Good_Num.setText(good.getquantity()+"");
                Good_Detail.setText(good.getbrief());
                good_pic.setImageBitmap(good.getphotoUrl());
            }
            @Override
            public void onFailure(Call<goodData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });

        Button buy =findViewById(R.id.buy);
        Button self =findViewById(R.id.self);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(MyGoodActivity.this);
                new AlertDialog.Builder(MyGoodActivity.this).setTitle("请输入出售价格")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                price = et.getText().toString();
                                if (price.equals("")) {
                                    Toast.makeText(getApplicationContext(), "出售价格不能为空！" + price, Toast.LENGTH_LONG).show();
                                }
                                else {
                                    requestBuy();
                                }
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyGoodActivity.this);//设置弹出框的第二种方法
                builder.setTitle("运输给自己");
                builder.setMessage("确定运输给自己吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestSelf();
                        MyGoodActivity.this.finish();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
    }

    public void requestBuy() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        GoodService request = retrofit.create(GoodService.class);
        Call<GoodPostdata> call = request.GoodSale(productId,Double.parseDouble(price));
        call.enqueue(new Callback<GoodPostdata>() {
            @Override
            public void onResponse(Call<GoodPostdata> call, Response<GoodPostdata> response) {
                System.out.println("连接成功");
                assert response.body() != null;
                if(response.body().getStatus()==1){
                    Toast.makeText(getApplicationContext(), "发布售卖成功！", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<GoodPostdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestSelf() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        GoodService request = retrofit.create(GoodService.class);
        Call<GoodPostdata> call = request.GoodSelf(productId);
        call.enqueue(new Callback<GoodPostdata>() {
            @Override
            public void onResponse(Call<GoodPostdata> call, Response<GoodPostdata> response) {
                System.out.println("连接成功");
                assert response.body() != null;
                if(response.body().getStatus()==1){
                    Toast.makeText(getApplicationContext(), "运输给自己成功！", Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<GoodPostdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
