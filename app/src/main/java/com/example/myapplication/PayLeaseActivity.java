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

import com.example.myapplication.Adapter.MyLandAdapter;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.entity.lease;
import com.example.myapplication.entity.leaseList;
import com.example.myapplication.service.LeaseService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayLeaseActivity extends AppCompatActivity {
    private List<lease> landList = new ArrayList();
    private MyLandAdapter mAdapter=new MyLandAdapter(landList);
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
        setContentView(R.layout.activity_pay_lease);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayLeaseActivity.this.finish();
            }
        });
        rv = findViewById(R.id.recyclerView);
        smart= findViewById(R.id.smart);
        requestLease();
        initRecyc();
    }

    private void initRecyc() {
        mAdapter=new MyLandAdapter(landList);
        mAdapter.setOnItemClickListener(new MyLandAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(PayLeaseActivity.this, PayLandActivity.class);
                intent.putExtra("landId",landList.get(position).getlandId());
                intent.putExtra("userId",userId);
                String dateTime = landList.get(position).getBeginTime();
                intent.putExtra("dateTime",dateTime);
                PayLeaseActivity.this.startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(PayLeaseActivity.this);
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

    public void requestLease() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LeaseService request = retrofit.create(LeaseService.class);
        Call<leaseList> call = request.getItem(userId);
        call.enqueue(new Callback<leaseList>() {
            @Override
            public void onResponse(Call<leaseList> call, Response<leaseList> response) {
                List<lease> lands=response.body().getData();
                System.out.println("连接成功");
                for(lease order:lands){
                    if(order.getstate()== lease.lease_State.unpaid){
                        landList.add(order);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<leaseList> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
