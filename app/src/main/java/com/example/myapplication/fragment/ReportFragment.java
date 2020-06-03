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

import com.example.myapplication.Adapter.ReportAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ReportLandActivity;
import com.example.myapplication.ReportProductActivity;
import com.example.myapplication.entity.accusation;
import com.example.myapplication.entity.reportList;
import com.example.myapplication.service.AdminService;
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

public class ReportFragment extends Fragment {
    private List<accusation> reportList = new ArrayList();
    private ReportAdapter mAdapter=new ReportAdapter(reportList);
    private SmartRefreshLayout smart;
    private RecyclerView rv;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        rv = view.findViewById(R.id.recyclerView);
        smart= view.findViewById(R.id.smart);
        context=getActivity();
        requestReport();
        initRecyc();
        return view;
    }

    private void initRecyc() {
        mAdapter=new ReportAdapter(reportList);
        mAdapter.setOnItemClickListener(new ReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent();
                if(reportList.get(position).getType()== accusation.InfoType.land){
                    intent=new Intent(context, ReportLandActivity.class);
                    intent.putExtra("uid",reportList.get(position).getUid());
                    intent.putExtra("InfoId",reportList.get(position).getInfoId());
                    intent.putExtra("reason",reportList.get(position).getReason());
                }else if(reportList.get(position).getType()== accusation.InfoType.product){
                    intent=new Intent(context, ReportProductActivity.class);
                    intent.putExtra("uid",reportList.get(position).getUid());
                    intent.putExtra("InfoId",reportList.get(position).getInfoId());
                    intent.putExtra("reason",reportList.get(position).getReason());
                }
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

    public void requestReport() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        AdminService request = retrofit.create(AdminService.class);
        Call<reportList> call = request.getAccusation(accusation.State.unhandled);
        call.enqueue(new Callback<com.example.myapplication.entity.reportList>() {
            @Override
            public void onResponse(Call<reportList> call, Response<reportList> response) {
                List<accusation> accusations=response.body().getData();
                System.out.println("连接成功");
                reportList.addAll(accusations);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<reportList> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
