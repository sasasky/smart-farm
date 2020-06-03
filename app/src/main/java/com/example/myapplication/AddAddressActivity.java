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

import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.service.AddressService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddAddressActivity extends AppCompatActivity {
    private String name;
    private String phone;
    private String address;
    private String postcode;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        userId = i.getStringExtra("userId");
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
                AddAddressActivity.this.finish();
            }
        });
        final EditText edit_myname =findViewById(R.id.add_myname);
        final EditText edit_myphone =findViewById(R.id.add_myphone);
        final EditText edit_myaddress =findViewById(R.id.add_myaddress);
        final EditText edit_mypostcode =findViewById(R.id.add_mypostcode);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edit_myname.getText().toString().trim();
                phone = edit_myphone.getText().toString().trim();
                address = edit_myaddress.getText().toString().trim();
                postcode = edit_mypostcode.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(AddAddressActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(AddAddressActivity.this, "请输入电话", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(address)){
                    Toast.makeText(AddAddressActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(postcode)){
                    Toast.makeText(AddAddressActivity.this, "请输入邮编", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddAddressActivity.this);//设置弹出框的第二种方法
                    builder.setTitle("发布");
                    builder.setMessage("确定发布吗?");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestAdd();
                        }
                    });
                    builder.setNegativeButton("否", null);
                    builder.show();
                }
            }
        });
    }

    public void requestAdd() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        AddressService request = retrofit.create(AddressService.class);
        Call<LandPostdata> call = request.AddResult(userId,postcode,address,name,phone);
        call.enqueue(new Callback<LandPostdata>() {
            @Override
            public void onResponse(Call<LandPostdata> call, Response<LandPostdata> response) {
                System.out.println("连接成功");
                System.out.println(response.code());
                assert response.body() != null;
                if(response.body().getStatus()==1){
                    Toast.makeText(AddAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    AddAddressActivity.this.finish();
                }
                else Toast.makeText(AddAddressActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LandPostdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
