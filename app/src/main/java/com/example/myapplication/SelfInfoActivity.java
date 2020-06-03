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

public class SelfInfoActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_self_info);
        rv = findViewById(R.id.recyclerView);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelfInfoActivity.this.finish();
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
        Call<orderSelfData> call = request.getSelfInfo(orderId);
        call.enqueue(new Callback<orderSelfData>() {
            @Override
            public void onResponse(Call<orderSelfData> call, Response<orderSelfData> response) {
                orderSelf orderinfo=response.body().getData();
                time.setText(orderinfo.getTime());
                state.setText("已支付");
                name.setText(orderinfo.getName());
                phone.setText(orderinfo.getPhone());
                address.setText(orderinfo.getAddress());
                sum.setText(Double.toString(orderinfo.getLogisticsFee()));
                logisticsId.setText(orderinfo.getLogisticsId());
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
}
