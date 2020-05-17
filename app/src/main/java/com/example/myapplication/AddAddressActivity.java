package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_add_address);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(AddAddressActivity.this, MyActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        EditText edit_name =findViewById(R.id.edit_name);
        EditText edit_phone =findViewById(R.id.edit_phone);
        EditText edit_address =findViewById(R.id.edit_address);
        final String name = edit_name.getText().toString();
        final String phone = edit_phone.getText().toString();
        final String address = edit_address.getText().toString();
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(AddAddressActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(AddAddressActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(address)){
                    Toast.makeText(AddAddressActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAddressActivity.this);//设置弹出框的第二种方法
                    builder.setTitle("发布");
                    builder.setMessage("确定发布吗?");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent it = new Intent(AddAddressActivity.this, MyActivity.class);//启动MainActivity
                            startActivity(it);
                        }
                    });
                    builder.setNegativeButton("否", null);
                    builder.show();
                }
            }
        });
    }
}
