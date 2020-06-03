package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Adapter.ProductAdapter;
import com.example.myapplication.Adapter.User1LandAdapter;
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goods;
import com.example.myapplication.entity.order;
import com.example.myapplication.entity.orderData;
import com.example.myapplication.service.GoodService;
import com.example.myapplication.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderInfoActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_order_info);
        rv = findViewById(R.id.recyclerView);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderInfoActivity.this.finish();
            }
        });
        final TextView time=findViewById(R.id.time);
        final TextView state=findViewById(R.id.state);
        final TextView name=findViewById(R.id.name);
        final TextView phone=findViewById(R.id.phone);
        final TextView address=findViewById(R.id.address);
        final TextView sum=findViewById(R.id.sum);
        final TextView logisticsId=findViewById(R.id.logisticsId);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        OrderService request = retrofit.create(OrderService.class);
        Call<orderData> call = request.getItem(orderId);
        call.enqueue(new Callback<orderData>() {
            @Override
            public void onResponse(Call<orderData> call, Response<orderData> response) {
                order orderinfo=response.body().getData();
                time.setText(orderinfo.getTime());
                if(orderinfo.getOrderState()== com.example.myapplication.entity.order.State.paid) {
                    state.setText("已支付");
                }else if(orderinfo.getOrderState()== com.example.myapplication.entity.order.State.overdue) {
                    state.setText("逾期未支付");
                }else if(orderinfo.getOrderState()== com.example.myapplication.entity.order.State.cancelled) {
                    state.setText("已取消");
                }else{
                    state.setText("待支付");
                }
                name.setText(orderinfo.getName());
                phone.setText(orderinfo.getPhone());
                address.setText(orderinfo.getAddress());
                sum.setText(Double.toString(orderinfo.getTotal()));
                logisticsId.setText(orderinfo.getLogisticsId());
                List<goods> good=orderinfo.getProductList();
                product.addAll(good);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<orderData> call, Throwable throwable) {
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
}
