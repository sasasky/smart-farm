package com.example.myapplication.fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.User2LandAdapter;
import com.example.myapplication.AllListActivity;
import com.example.myapplication.LandDetailActivity;
import com.example.myapplication.OrderInfoActivity;
import com.example.myapplication.PayProductActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.entity.order;
import com.example.myapplication.entity.updateTime;
import com.example.myapplication.service.LandService;
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

public class User1MainFragment extends Fragment {
    private List<land> landList = new ArrayList();
    private User2LandAdapter mAdapter=new User2LandAdapter(landList);
    private SmartRefreshLayout smart;
    private RecyclerView rv;
    private Context context;
    private String userId;
    private String time;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2_main, container, false);
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
        Call<updateTime> call = request.getLandTime();
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
        mAdapter=new User2LandAdapter(landList);
        mAdapter.setOnItemClickListener(new User2LandAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                if (landList.get(position).getstate()== com.example.myapplication.entity.land.land_State.leased){
                    Toast.makeText(context, "该土地已被租赁！", Toast.LENGTH_SHORT).show();
                }else {
                    intent.setClass(context, LandDetailActivity.class);
                    intent.putExtra("landId",landList.get(position).getlandId());
                    intent.putExtra("userId",userId);
                    context.startActivity(intent);
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
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
        LandService request = retrofit.create(LandService.class);
        Call<landlist> call = request.getItem();
        call.enqueue(new Callback<landlist>() {
            @Override
            public void onResponse(Call<landlist> call, Response<landlist> response) {
                List<land> lands=response.body().getData();
                System.out.println("连接成功");
                landList.addAll(lands);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<landlist> call, Throwable throwable) {
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
        Call<updateTime> call = request.getLandTime();
        call.enqueue(new Callback<updateTime>() {
            @Override
            public void onResponse(Call<updateTime> call, Response<updateTime> response) {
                Date uptime1=response.body().getData();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
                String time1=sdf.format(uptime1);
                if(!time.equals(time1)){
                    time=time1;
                    Toast.makeText(context.getApplicationContext(), "可租用土地列表已更新，请下拉刷新！", Toast.LENGTH_LONG).show();
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