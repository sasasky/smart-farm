package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_goods_detail);
        TextView back =findViewById(R.id.button_backward);
        TextView main_image =findViewById(R.id.main_image);
        TextView cart_image =findViewById(R.id.cart_image);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        main_image.setTypeface(font);
        cart_image.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(GoodsDetailActivity.this, User3MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        main_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(GoodsDetailActivity.this, User3MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        cart_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(GoodsDetailActivity.this, User3MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        ImageView good_pic =findViewById(R.id.good_pic);
        TextView Good_Name =findViewById(R.id.Good_Name);
        TextView Good_Price =findViewById(R.id.Good_Price);
        TextView Good_Detail =findViewById(R.id.Good_Detail);
        good_pic.setImageResource(R.drawable.carrot);
        Good_Name.setText("新鲜玉米 甜玉米 爆浆玉米 8斤装");
        Good_Price.setText("300元起");
        Good_Detail.setText("新鲜玉米 甜玉米 爆浆玉米 8斤装 绿色有机 美味健康 经过180天精心种植 放心食品 欢迎购买");
        Button add_cart= findViewById(R.id.add_cart);
        Button pay= findViewById(R.id.pay);
    }
}
