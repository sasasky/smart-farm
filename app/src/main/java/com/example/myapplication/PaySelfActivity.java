package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.entity.Putdata;
import com.example.myapplication.entity.goods;
import com.example.myapplication.entity.order;
import com.example.myapplication.entity.orderData;
import com.example.myapplication.entity.orderSelf;
import com.example.myapplication.entity.orderSelfData;
import com.example.myapplication.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaySelfActivity extends AppCompatActivity {
    private int orderId;
    private RecyclerView rv;
    private List<goods> product = new ArrayList<>();
    private ProductAdapter mAdapter=new ProductAdapter(product);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        orderId = i.getIntExtra("orderId",0);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_pay_self);
        rv = findViewById(R.id.recyclerView);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaySelfActivity.this.finish();
            }
        });
        final TextView time=findViewById(R.id.time);
        final TextView state=findViewById(R.id.state);
        final TextView name=findViewById(R.id.name);
        final TextView phone=findViewById(R.id.phone);
        final TextView address=findViewById(R.id.address);
        final TextView sum=findViewById(R.id.sum);
        Button pay=findViewById(R.id.pay);
        Button cancel=findViewById(R.id.cancel);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaySelfActivity.this);//设置弹出框的第二种方法
                builder.setTitle("支付");
                builder.setMessage("确定支付吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPay();
                        PaySelfActivity.this.finish();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PaySelfActivity.this);//设置弹出框的第二种方法
                builder.setTitle("取消订单");
                builder.setMessage("确定取消订单吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestCancel();
                        PaySelfActivity.this.finish();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        OrderService request = retrofit.create(OrderService.class);
        Call<orderSelfData> call = request.getSelfInfo(orderId);
        call.enqueue(new Callback<orderSelfData>() {
            @Override
            public void onResponse(Call<orderSelfData> call, Response<orderSelfData> response) {
                orderSelf orderinfo=response.body().getData();
                time.setText(orderinfo.getTime());
                state.setText("待支付");
                name.setText(orderinfo.getName());
                phone.setText(orderinfo.getPhone());
                address.setText(orderinfo.getAddress());
                sum.setText(Double.toString(orderinfo.getLogisticsFee()));
                List<goods> good=orderinfo.getProductList();
                product.addAll(good);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<orderSelfData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }

    public void requestPay() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        OrderService request = retrofit.create(OrderService.class);
        Call<Putdata> call = request.payForSelfOrder(orderId);
        call.enqueue(new Callback<Putdata>() {
            @Override
            public void onResponse(Call<Putdata> call, Response<Putdata> response) {
                System.out.println("连接成功");
                System.out.println(response.message()+"   "+response.body());
                Toast.makeText(PaySelfActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Putdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestCancel() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        OrderService request = retrofit.create(OrderService.class);
        Call<Putdata> call = request.deleteItem(orderId);
        call.enqueue(new Callback<Putdata>() {
            @Override
            public void onResponse(Call<Putdata> call, Response<Putdata> response) {
                System.out.println("连接成功");
                System.out.println(response.message()+"   "+response.body());
                Toast.makeText(PaySelfActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Putdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
