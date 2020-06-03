package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AdminLandAdapter;
import com.example.myapplication.Adapter.User2LandAdapter;
import com.example.myapplication.CheckLandActivity;
import com.example.myapplication.LandDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.service.AdminService;
import com.example.myapplication.service.LandService;
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

public class CheckFragment extends Fragment {
    private List<land> landList = new ArrayList();
    private AdminLandAdapter mAdapter=new AdminLandAdapter(landList);
    private SmartRefreshLayout smart;
    private RecyclerView rv;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        rv = view.findViewById(R.id.recyclerView);
        smart= view.findViewById(R.id.smart);
        context=getActivity();
        requestCheck();
        initRecyc();
        return view;
    }

    private void initRecyc() {
        mAdapter=new AdminLandAdapter(landList);
        mAdapter.setOnItemClickListener(new AdminLandAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(context, CheckLandActivity.class);
                intent.putExtra("landId",landList.get(position).getlandId());
                context.startActivity(intent);
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

    public void requestCheck() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        AdminService request = retrofit.create(AdminService.class);
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
}
