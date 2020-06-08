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

import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.accusation;
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goods;
import com.example.myapplication.service.CartService;
import com.example.myapplication.service.GoodService;
import com.example.myapplication.service.UserService;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoodsDetailActivity extends AppCompatActivity {
    private int productId;
    private String reason;
    private String userId;
    private List<Integer> pIdlist= new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        productId = i.getIntExtra("productId",0);
        userId = i.getStringExtra("userId");
        pIdlist.add(productId);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_goods_detail);
        TextView back =findViewById(R.id.button_backward);
        TextView report =findViewById(R.id.button_report);
        TextView main_image =findViewById(R.id.main_image);
        TextView cart_image =findViewById(R.id.cart_image);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        report.setTypeface(font);
        main_image.setTypeface(font);
        cart_image.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsDetailActivity.this.finish();
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText et = new EditText(GoodsDetailActivity.this);
                new AlertDialog.Builder(GoodsDetailActivity.this).setTitle("请输入举报原因")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                reason = et.getText().toString();
                                if (reason.equals("")) {
                                    Toast.makeText(getApplicationContext(), "举报原因不能为空！" + reason, Toast.LENGTH_LONG).show();
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
        main_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(GoodsDetailActivity.this, User3MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        cart_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(GoodsDetailActivity.this, User3MainActivity.class);//启动MainActivity
                startActivity(it);
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
                Good_Price.setText(good.getprice()+"");
                Good_Detail.setText(good.getbrief());
                good_pic.setImageBitmap(good.getphotoUrl());
            }
            @Override
            public void onFailure(Call<goodData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
        Button add_cart= findViewById(R.id.add_cart);
        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCart();
            }
        });
        Button pay= findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(pIdlist);
                Intent it=new Intent(GoodsDetailActivity.this, SelectAddressActivity.class);//启动MainActivity
                it.putIntegerArrayListExtra("pIdlist", (ArrayList<Integer>) pIdlist);
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
    }

    public void requestCart() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        CartService request = retrofit.create(CartService.class);
        Call<goodData> call = request.postResult(userId,productId);
        call.enqueue(new Callback<goodData>() {
            @Override
            public void onResponse(Call<goodData> call, Response<goodData> response) {
                System.out.println("连接成功");
                Toast.makeText(GoodsDetailActivity.this, "加入购物车成功！", Toast.LENGTH_SHORT).show();
                System.out.println(response.code());
            }
            @Override
            public void onFailure(Call<goodData> call, Throwable throwable) {
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
        UserService request = retrofit.create(UserService.class);
        Call<LandPostdata> call = request.accuseResult(userId,productId, accusation.InfoType.product,reason);
        call.enqueue(new Callback<LandPostdata>() {
            @Override
            public void onResponse(Call<LandPostdata> call, Response<LandPostdata> response) {
                System.out.println("连接成功");
                assert response.body() != null;
                if(response.body().getStatus()==1)
                    Toast.makeText(GoodsDetailActivity.this, "举报成功", Toast.LENGTH_SHORT).show();
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
