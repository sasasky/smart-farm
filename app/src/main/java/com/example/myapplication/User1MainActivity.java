package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.Adapter.User1LandAdapter;
import com.example.myapplication.Adapter.User2LandAdapter;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.service.LandService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User1MainActivity extends Activity {
    private List<land> landList = new ArrayList();
    private User1LandAdapter mAdapter=new User1LandAdapter(landList);
    private ListView mListView;
    private SmartRefreshLayout smart;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user1_main);
        rv = findViewById(R.id.recyclerView);
        smart= findViewById(R.id.smart);
        request();
        initRecyc();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(User1MainActivity.this, AddLandActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
    }

    private void initRecyc() {
        mAdapter=new User1LandAdapter(landList);
        mAdapter.setOnItemClickListener(MyItemClickListener);
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

    private User1LandAdapter.OnItemClickListener MyItemClickListener = new User1LandAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()){
                case R.id.detail:
                    Intent it=new Intent(User1MainActivity.this, ChangeLandActivity.class);//启动MainActivity
                    startActivity(it);
                    break;
                case R.id.delete:
                    AlertDialog.Builder builder = new AlertDialog.Builder(User1MainActivity.this);//设置弹出框的第二种方法
                    builder.setTitle("删除");
                    builder.setMessage("确定删除吗?");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int i) {
                            landList.remove(i);
                            mAdapter.notifyItemRemoved(i);
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                                    .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                                    .build();

                            // 步骤5:创建 网络请求接口 的实例
                            LandService request = retrofit.create(LandService.class);

                            //对 发送请求 进行封装
                            Call<landlist> call = request.putInfo(landList.get(i).getlandId());

                            //步骤6:发送网络请求(异步)
                            call.enqueue(new Callback<landlist>() {
                                //请求成功时候的回调
                                @Override
                                public void onResponse(Call<landlist> call, Response<landlist> response) {
                                    System.out.println("连接成功");
                                    landList.get(i).setstate("cancelled");
                                }

                                //请求失败时候的回调
                                @Override
                                public void onFailure(Call<landlist> call, Throwable throwable) {
                                    System.out.println("连接失败");
                                    System.out.println(throwable.getMessage());
                                }
                            });
                            mAdapter.notifyItemChanged(0,landList.size());
                        }
                    });
                    builder.setNegativeButton("否", null);
                    builder.show();
                    break;
            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Android.R.id.home对应应用程序图标的id
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        LandService request = retrofit.create(LandService.class);

        //对 发送请求 进行封装
        Call<landlist> call = request.getItem("12345678901");

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<landlist>() {
            //请求成功时候的回调
            @Override
            public void onResponse(Call<landlist> call, Response<landlist> response) {
                List<land> lands=response.body().getData();
                landList.addAll(lands);
                mAdapter.notifyDataSetChanged();
            }

            //请求失败时候的回调
            @Override
            public void onFailure(Call<landlist> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}