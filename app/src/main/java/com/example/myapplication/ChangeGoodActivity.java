package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ChangeGoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_change_good);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ChangeGoodActivity.this, User2MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        ImageView good_pic =findViewById(R.id.good_pic);
        EditText good_name =findViewById(R.id.edit_name);
        EditText good_price =findViewById(R.id.edit_price);
        EditText good_num =findViewById(R.id.edit_num);
        good_pic.setImageResource(R.drawable.carrot);
        good_name.setText("新鲜玉米 甜玉米 爆浆玉米 8斤装");
        good_price.setText("300元起");
        good_num.setText("80斤");
    }
}
