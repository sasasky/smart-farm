package com.example.myapplication.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.User2GoodAdapter;
import com.example.myapplication.GoodsDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.goods;
import com.example.myapplication.entity.goodslist;
import com.example.myapplication.entity.updateTime;
import com.example.myapplication.service.GoodService;
import com.example.myapplication.service.UpdateService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User2MainFragment  extends Fragment {
    private List<goods> good = new ArrayList<>();
    private User2GoodAdapter mAdapter;
    private SmartRefreshLayout smart;
    private RecyclerView rv;
    private Context context;
    private String userId;
    private String time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2_goods, container, false);
        Bundle bundle=getArguments();
        if(bundle!=null){
            userId=bundle.getString("userId");
        }
        rv = view.findViewById(R.id.recyclerView);
        smart= view.findViewById(R.id.smart);
        context=getActivity();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        UpdateService request = retrofit.create(UpdateService.class);
        Call<updateTime> call = request.getProductTime();
        call.enqueue(new Callback<updateTime>() {
            @Override
            public void onResponse(Call<updateTime> call, Response<updateTime> response) {
                Date uptime=response.body().getData();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
                time=sdf.format(uptime);
            }
            @Override
            public void onFailure(Call<updateTime> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
        final Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            public void run(){
                requestTime();
                timer.cancel();
            }
        }, 1000);
        request();
        initRecyc();
        return view;
    }
    private void initRecyc() {
        mAdapter=new User2GoodAdapter(good);
        mAdapter.setOnItemClickListener(new User2GoodAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                if (good.get(position).getGoodState()== goods.Good_State.sold_out){
                    Toast.makeText(context, "该商品已被购买！", Toast.LENGTH_SHORT).show();
                }else {
                    intent.setClass(context, GoodsDetailActivity.class);
                    intent.putExtra("productId",good.get(position).getproductId());
                    intent.putExtra("userId",userId);
                    context.startActivity(intent);
                }
            }
            @Override
            public void onItemLongClick(final int position) {
            }
        });
        GridLayoutManager layoutManage = new GridLayoutManager(context, 2);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(layoutManage);
        smart.setEnableRefresh(false);
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                request();
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

    public void request() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        GoodService request = retrofit.create(GoodService.class);
        Call<goodslist> call = request.getItem();
        call.enqueue(new Callback<goodslist>() {
            @Override
            public void onResponse(Call<goodslist> call, Response<goodslist> response) {
                System.out.println("连接成功");
                List<goods> goods=response.body().getData();
                good.addAll(goods);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<goodslist> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestTime() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        UpdateService request = retrofit.create(UpdateService.class);
        Call<updateTime> call = request.getProductTime();
        call.enqueue(new Callback<updateTime>() {
            @Override
            public void onResponse(Call<updateTime> call, Response<updateTime> response) {
                Date uptime1=response.body().getData();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
                String time1=sdf.format(uptime1);
                if(!time.equals(time1)){
                    time=time1;
                    Toast.makeText(context.getApplicationContext(), "可购买商品列表已更新，请下拉刷新！", Toast.LENGTH_LONG).show();
                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<updateTime> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}