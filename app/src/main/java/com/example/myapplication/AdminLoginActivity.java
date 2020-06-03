package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.entity.adminData;
import com.example.myapplication.service.AdminService;
import com.example.myapplication.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminLoginActivity extends AppCompatActivity {
    private EditText phoneEditText;
    private EditText passwordEditText;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_admin_login);
        TextView button_phone =findViewById(R.id.button_phone);
        TextView button_pw1 =findViewById(R.id.button_pw);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        button_phone.setTypeface(font);
        button_pw1.setTypeface(font);
        phoneEditText = findViewById(R.id.phone_num);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(AdminLoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(AdminLoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                            .build();
                    AdminService request = retrofit.create(AdminService.class);
                    Call<adminData> call = request.login(Integer.parseInt(phone),password);
                    call.enqueue(new Callback<adminData>() {
                        @Override
                        public void onResponse(Call<adminData> call, Response<adminData> response) {
                            SharedPreferences sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putBoolean("isLogin", true);
                            editor.putString("phone",phone);
                            editor.apply();
                            saveLoginStatus(true, phone);
                            //登录成功后关闭此页面进入主页
                            Intent data=new Intent();
                            //datad.putExtra( ); name , value ;
                            data.putExtra("isLogin",true);
                            //RESULT_OK为Activity系统常量，状态码为-1
                            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                            setResult(RESULT_OK,data);
                            //销毁登录界面
                            AdminLoginActivity.this.finish();
                            //跳转到主界面，登录成功的状态传递到 MainActivity 中
                            startActivity(new Intent(AdminLoginActivity.this, AdminMainActivity.class));
                        }
                        @Override
                        public void onFailure(Call<adminData> call, Throwable throwable) {
                            System.out.println("连接失败");
                            System.out.println(throwable.getMessage());
                        }
                    });
                    return;
                }
            }
        });
    }

    private void saveLoginStatus(boolean status,String phone){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", phone);
        //提交修改
        editor.commit();
    }
}
