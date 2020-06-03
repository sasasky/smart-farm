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

import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goods;
import com.example.myapplication.entity.orderData;
import com.example.myapplication.service.GoodService;
import com.example.myapplication.service.OrderService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderSelfActivity extends AppCompatActivity {
    private String name;
    private String phone;
    private String address;
    private String postcode;
    private String userId;
    private int productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        userId = i.getStringExtra("userId");
        name = i.getStringExtra("name");
        phone = i.getStringExtra("phone");
        address = i.getStringExtra("address");
        postcode = i.getStringExtra("postcode");
        productId = i.getIntExtra("productId",0);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_order_self);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSelfActivity.this.finish();
            }
        });
        final TextView nameText=findViewById(R.id.name);
        final TextView phoneText=findViewById(R.id.phone);
        final TextView addressText=findViewById(R.id.address);
        final ImageView pic=findViewById(R.id.pic);
        final TextView title=findViewById(R.id.title);
        final TextView num=findViewById(R.id.num);
        nameText.setText(name);
        phoneText.setText(phone);
        addressText.setText(address);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        final GoodService request = retrofit.create(GoodService.class);
        Call<goodData> call = request.getItem(productId);
        call.enqueue(new Callback<goodData>() {
            @Override
            public void onResponse(Call<goodData> call, Response<goodData> response) {
                assert response.body() != null;
                goods good=response.body().getData();
                pic.setImageBitmap(good.getphotoUrl());
                title.setText(good.gettype());
                num.setText(good.getquantity()+"KG");
            }
            @Override
            public void onFailure(Call<goodData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });

        Button pay=findViewById(R.id.add);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderSelfActivity.this);//设置弹出框的第二种方法
                builder.setTitle("下单");
                builder.setMessage("确定下单吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestOrder();
                        Intent intent = new Intent();
                        intent.setClass(OrderSelfActivity.this, User2MainActivity.class);
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
    }

    public void requestOrder() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        OrderService request = retrofit.create(OrderService.class);
        Call<orderData> call = request.placeSelfOrder(userId,postcode,phone,name,address);
        call.enqueue(new Callback<orderData>() {
            @Override
            public void onResponse(Call<orderData> call, Response<orderData> response) {
                System.out.println("连接成功");
                if(response.body().getStatus()==1){
                    Toast.makeText(OrderSelfActivity.this, "下单成功,请尽快支付！", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<orderData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
