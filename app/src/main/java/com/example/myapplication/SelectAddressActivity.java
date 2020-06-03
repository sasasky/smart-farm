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
import com.example.myapplication.entity.address;
import com.example.myapplication.entity.addressList;
import com.example.myapplication.entity.goods;
import com.example.myapplication.service.AddressService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectAddressActivity extends AppCompatActivity {
    private List<address> mData = new ArrayList();
    private List<Integer> pIdList= new ArrayList();
    private int productId;
    private AddressAdapter mAdapter;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        Intent i = getIntent();
        productId = i.getIntExtra("productId",0);
        pIdList = i.getIntegerArrayListExtra("pIdlist");
        userId = i.getStringExtra("userId");
        setContentView(R.layout.activity_select_address);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAddressActivity.this.finish();
            }
        });
        RecyclerView rv = findViewById(R.id.recyclerView);
        requestAddress();
        mAdapter = new AddressAdapter(mData);
        mAdapter.setOnItemClickListener(new AddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                System.out.println(pIdList);
                Intent intent = new Intent();
                if(pIdList!=null){
                    intent.setClass(SelectAddressActivity.this, CreateOrderActivity.class);
                    intent.putIntegerArrayListExtra("pIdlist", (ArrayList<Integer>) pIdList);
                    intent.putExtra("userId",userId);
                    intent.putExtra("name",mData.get(position).getName());
                    intent.putExtra("phone",mData.get(position).getPhone());
                    intent.putExtra("postcode",mData.get(position).getPostcode());
                    intent.putExtra("address",mData.get(position).getDetail());
                    startActivity(intent);
                }else{
                    intent.setClass(SelectAddressActivity.this, OrderSelfActivity.class);
                    intent.putExtra("userId",userId);
                    intent.putExtra("productId",productId);
                    intent.putExtra("name",mData.get(position).getName());
                    intent.putExtra("phone",mData.get(position).getPhone());
                    intent.putExtra("postcode",mData.get(position).getPostcode());
                    intent.putExtra("address",mData.get(position).getDetail());
                    startActivity(intent);
                }

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }

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
