package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.User2ListAdapter;
import com.example.myapplication.entity.order;
import com.example.myapplication.entity.orderlist;
import com.example.myapplication.service.OrderService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllListActivity extends AppCompatActivity {
    private List<com.example.myapplication.entity.order> Order = new ArrayList<>();
    private User2ListAdapter mAdapter;
    private SmartRefreshLayout smart;
    private RecyclerView rv;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        userId = i.getStringExtra("userId");
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_all_list);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllListActivity.this.finish();
            }
        });
        rv = findViewById(R.id.recyclerView);
        smart= findViewById(R.id.smart);
        requestAll();
        initRecyc();
    }

    private void initRecyc() {
        mAdapter=new User2ListAdapter(Order);
        mAdapter.setOnItemClickListener(new User2ListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                if (Order.get(position).getOrderState()==order.State.unpaid){
                    intent.setClass(AllListActivity.this, PayProductActivity.class);
                    intent.putExtra("orderId",Order.get(position).getOrderId());
                    AllListActivity.this.startActivity(intent);
                }else if (Order.get(position).getOrderState()==order.State.paid){
                    intent.setClass(AllListActivity.this, OrderInfoActivity.class);
                    intent.putExtra("orderId",Order.get(position).getOrderId());
                    AllListActivity.this.startActivity(intent);
                }else if (Order.get(position).getOrderState()==order.State.cancelled){
                    Toast.makeText(AllListActivity.this, "该订单已被取消！", Toast.LENGTH_SHORT).show();
                }else if (Order.get(position).getOrderState()==order.State.overdue){
                    Toast.makeText(AllListActivity.this, "该订单逾期未支付！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
        smart.setEnableRefresh(false);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
            }
        });
    }

    public void requestAll() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        OrderService request = retrofit.create(OrderService.class);
        Call<orderlist> call = request.getItem(userId);
        call.enqueue(new Callback<orderlist>() {
            @Override
            public void onResponse(Call<orderlist> call, Response<orderlist> response) {
                List<order> orders=response.body().getData();
                if(orders==null){
                    Toast.makeText(AllListActivity.this, "没有订单", Toast.LENGTH_SHORT).show();
                }
                Order.addAll(orders);
                System.out.println(response.body().getMsg());
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<orderlist> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
