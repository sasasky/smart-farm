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

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {
    private List<address> mData;
    private AddressAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent it=new Intent(MyActivity.this, User3MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MyActivity.this, AddAddressActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        RecyclerView rv = findViewById(R.id.recyclerView);
        initData();
        mAdapter=new AddressAdapter(mData,MyActivity.this);
        mAdapter.setOnItemClickListener(MyItemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }
    private void initData() {
        //初始化数据 自己模拟的数据
        mData=new ArrayList<>();
        mData.add(new address("韩昊玥","18904425005","吉林省吉林市船营区紫荆城"));
        mData.add(new address("韩昊玥","18904425005","吉林省吉林市船营区紫荆城"));
        mData.add(new address("韩昊玥","18904425005","吉林省吉林市船营区紫荆城"));
    }

    private AddressAdapter.OnItemClickListener MyItemClickListener = new AddressAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()){
                case R.id.context:
                    Intent it=new Intent(MyActivity.this, ChangeAddressActivity.class);//启动MainActivity
                    startActivity(it);
                    break;
                case R.id.delete:
                    mData.remove((int)position);
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
