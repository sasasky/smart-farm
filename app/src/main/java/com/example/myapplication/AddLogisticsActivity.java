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

public class AddLogisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_add_logistics);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(AddLogisticsActivity.this, User2MainActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        EditText editText = findViewById(R.id.editText);
        final String title = editText.getText().toString();
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(title)){
                    Toast.makeText(AddLogisticsActivity.this, "请输入物流单号", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddLogisticsActivity.this);//设置弹出框的第二种方法
                    builder.setTitle("添加物流");
                    builder.setMessage("确定添加吗?");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent it = new Intent(AddLogisticsActivity.this, User2MainActivity.class);//启动MainActivity
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
