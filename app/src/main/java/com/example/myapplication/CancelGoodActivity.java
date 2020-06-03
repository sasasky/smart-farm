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

import com.example.myapplication.entity.GoodPostdata;
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goods;
import com.example.myapplication.service.GoodService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CancelGoodActivity extends AppCompatActivity {
    private int productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        productId = i.getIntExtra("productId",0);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_cancel_good);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelGoodActivity.this.finish();
            }
        });
        final ImageView good_pic =findViewById(R.id.good_pic);
        final TextView Good_Name =findViewById(R.id.Good_Name);
        final TextView Good_Price =findViewById(R.id.Good_Price);
        final TextView Good_Detail =findViewById(R.id.Good_Detail);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        final GoodService request = retrofit.create(GoodService.class);
        Call<goodData> call = request.getItem(productId);
        call.enqueue(new Callback<goodData>() {
            @Override
            public void onResponse(Call<goodData> call, Response<goodData> response) {
                goods good=response.body().getData();
                Good_Name.setText(good.gettype());
                Good_Price.setText(good.getquantity()+"KG");
                Good_Detail.setText(good.getbrief());
                good_pic.setImageBitmap(good.getphotoUrl());
            }
            @Override
            public void onFailure(Call<goodData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });

        Button cancel =findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CancelGoodActivity.this);//设置弹出框的第二种方法
                builder.setTitle("取消发布售卖");
                builder.setMessage("确定取消发布售卖吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestCancel();
                        CancelGoodActivity.this.finish();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
    }

    public void requestCancel() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        GoodService request = retrofit.create(GoodService.class);
        Call<GoodPostdata> call = request.CancelSale(productId);
        call.enqueue(new Callback<GoodPostdata>() {
            @Override
            public void onResponse(Call<GoodPostdata> call, Response<GoodPostdata> response) {
                System.out.println("连接成功");
                assert response.body() != null;
                if(response.body().getStatus()==1){
                    Toast.makeText(getApplicationContext(), "取消发布售卖成功！", Toast.LENGTH_LONG).show();
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
