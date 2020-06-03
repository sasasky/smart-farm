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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.address;
import com.example.myapplication.entity.addressData;
import com.example.myapplication.service.AddressService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangeAddressActivity extends AppCompatActivity {
    private int addressId;
    private String name;
    private String phone;
    private String address;
    private String postcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        Intent i = getIntent();
        addressId = i.getIntExtra("addressId",0);
        setContentView(R.layout.activity_change_address);
        TextView back =findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeAddressActivity.this.finish();
            }
        });
        TextView delete =findViewById(R.id.button_delete);
        delete.setTypeface(font);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeAddressActivity.this);//设置弹出框的第二种方法
                builder.setTitle("删除");
                builder.setMessage("确定删除吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestDel();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
        final EditText edit_name =findViewById(R.id.edit_name);
        final EditText edit_phone =findViewById(R.id.edit_phone);
        final EditText edit_address =findViewById(R.id.edit_address);
        final EditText edit_postcode =findViewById(R.id.edit_postcode);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        final AddressService request = retrofit.create(AddressService.class);
        Call<addressData> call = request.getItemInfo(addressId);
        call.enqueue(new Callback<addressData>() {
            @Override
            public void onResponse(Call<addressData> call, Response<addressData> response) {
                address addr=response.body().getData();
                edit_name.setText(addr.getName());
                edit_phone.setText(addr.getPhone());
                edit_address.setText(addr.getDetail());
                edit_postcode.setText(addr.getPostcode());
            }
            @Override
            public void onFailure(Call<addressData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
        Button change = findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name =edit_name.getText().toString();
                phone =edit_phone.getText().toString();
                address =edit_address.getText().toString();
                postcode =edit_postcode.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeAddressActivity.this);//设置弹出框的第二种方法
                builder.setTitle("修改");
                builder.setMessage("确定修改吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestAddr();
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
    }

    public void requestAddr() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        AddressService request = retrofit.create(AddressService.class);
        Call<LandPostdata> call = request.postResult(addressId,postcode,address,name,phone);
        call.enqueue(new Callback<LandPostdata>() {
            @Override
            public void onResponse(Call<LandPostdata> call, Response<LandPostdata> response) {
                System.out.println("连接成功");
                assert response.body() != null;
                if(response.body().getStatus()==1){
                    Toast.makeText(ChangeAddressActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    ChangeAddressActivity.this.finish();
                }
                else Toast.makeText(ChangeAddressActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<LandPostdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestDel() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        AddressService request = retrofit.create(AddressService.class);
        Call<LandPostdata> call = request.deleteItem(addressId);
        call.enqueue(new Callback<LandPostdata>() {
            @Override
            public void onResponse(Call<LandPostdata> call, Response<LandPostdata> response) {
                System.out.println("连接成功");
                assert response.body() != null;
                if(response.body().getStatus()==1){
                    Toast.makeText(ChangeAddressActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    ChangeAddressActivity.this.finish();
                }
                else System.out.println(response.body().getMsg());
            }
            @Override
            public void onFailure(Call<LandPostdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
