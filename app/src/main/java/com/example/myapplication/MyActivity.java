package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Adapter.AddressAdapter;
import com.example.myapplication.Adapter.User2LandAdapter;
import com.example.myapplication.entity.ShopInfo;
import com.example.myapplication.entity.ShopInfoList;
import com.example.myapplication.entity.address;
import com.example.myapplication.entity.addressList;
import com.example.myapplication.service.AddressService;
import com.example.myapplication.service.CartService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyActivity extends AppCompatActivity {
    private List<address> mData = new ArrayList();
    private AddressAdapter mAdapter;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        userId = i.getStringExtra("userId");
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_my);
        TextView back =findViewById(R.id.button_backward);
        TextView add =findViewById(R.id.button_add);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        add.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivity.this.finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MyActivity.this, AddAddressActivity.class);//启动MainActivity
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
        RecyclerView rv = findViewById(R.id.recyclerView);
        requestAddress();
        mAdapter = new AddressAdapter(mData);
        mAdapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent=new Intent(MyActivity.this, ChangeAddressActivity.class);
                intent.putExtra("addressId",mData.get(position).getAddressId());
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }

    private AddressAdapter.OnItemClickListener MyItemClickListener = new AddressAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()){
                case R.id.context:
                    Intent it=new Intent(MyActivity.this, ChangeAddressActivity.class);//启动MainActivity
                    startActivity(it);
                    break;
            }
        }
    };

    public void requestAddress() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        AddressService request = retrofit.create(AddressService.class);
        Call<addressList> call = request.getItem(userId);
        call.enqueue(new Callback<addressList>() {
            @Override
            public void onResponse(Call<addressList> call, Response<addressList> response) {
                assert response.body() != null;
                List<address> address= response.body().getData();
                mData.addAll(address);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<addressList> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
