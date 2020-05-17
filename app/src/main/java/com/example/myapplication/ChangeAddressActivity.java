package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ChangeAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_change_address);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(ChangeAddressActivity.this, MyActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        EditText good_name =findViewById(R.id.edit_name);
        EditText edit_phone =findViewById(R.id.edit_phone);
        EditText edit_address =findViewById(R.id.edit_address);
        good_name.setText("韩昊玥");
        edit_phone.setText("18904425005");
        edit_address.setText("吉林省吉林市船营区紫荆城");
        Button change = findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeAddressActivity.this);//设置弹出框的第二种方法
                builder.setTitle("修改");
                builder.setMessage("确定修改吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(ChangeAddressActivity.this, MyActivity.class);//启动MainActivity
                        startActivity(it);
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
    }
}
