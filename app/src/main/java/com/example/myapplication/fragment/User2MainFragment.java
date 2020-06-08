package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.User2GoodAdapter;
import com.example.myapplication.GoodsDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.goods;
import com.example.myapplication.entity.goodslist;
import com.example.myapplication.service.GoodService;
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

public class User2MainFragment  extends Fragment {
    private List<goods> good = new ArrayList<>();
    private User2GoodAdapter mAdapter;
    private ListView mListView;
    private SmartRefreshLayout smart;
    private RecyclerView rv;
    private Context context;
    private String userId;
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
                intent.setClass(context, GoodsDetailActivity.class);
                intent.putExtra("productId",good.get(position).getproductId());
                intent.putExtra("userId",userId);
                context.startActivity(intent);
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
}