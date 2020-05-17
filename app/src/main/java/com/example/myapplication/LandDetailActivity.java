package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LandDetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_land_detail);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(LandDetailActivity.this, User2MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        TextView Land_Name =findViewById(R.id.Land_Name);
        TextView Land_Locate =findViewById(R.id.Land_Locate);
        TextView Land_price =findViewById(R.id.Land_price);
        TextView Land_area =findViewById(R.id.Land_area);
        TextView water =findViewById(R.id.water);
        TextView warm =findViewById(R.id.warm);
        TextView CO2 =findViewById(R.id.CO2);
        TextView light =findViewById(R.id.light);
        ImageView Land_Pic =findViewById(R.id.Land_Pic);
        Land_Name.setText("潼南区蔬菜基地");
        Land_Locate.setText("位于重庆市潼南区11组蔬菜基地");
        Land_price.setText("300元起");
        Land_area.setText("100㎡");
        water.setText("5%");
        warm.setText("20.8℃");
        CO2.setText("474ppm");
        light.setText("0lux");
        Land_Pic.setImageResource(R.drawable.land);
    }
}
