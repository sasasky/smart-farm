package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.Adapter.User3PayListAdapter;
import com.example.myapplication.entity.list;

import java.util.ArrayList;
import java.util.List;

public class PayListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_pay_list);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(PayListActivity.this, User3MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        RecyclerView rv = findViewById(R.id.recyclerView);
        List<list> listList = new ArrayList();
        listList.add(new list("2020-02-20","待付款","新鲜玉米 甜玉米 爆浆玉米 8斤装","￥8000.00","x2","16000.00",R.drawable.carrot));
        listList.add(new list("2020-02-20","待付款","新鲜玉米 甜玉米 爆浆玉米 8斤装","￥8000.00","x2","16000.00",R.drawable.carrot));
        listList.add(new list("2020-02-20","待付款","新鲜玉米 甜玉米 爆浆玉米 8斤装","￥8000.00","x2","16000.00",R.drawable.carrot));
        User3PayListAdapter mAdapter=new User3PayListAdapter(listList);
        mAdapter.setOnItemClickListener(new User3PayListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
    }
}
