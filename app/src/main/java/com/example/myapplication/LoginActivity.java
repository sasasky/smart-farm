package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.service.LandService;
import com.example.myapplication.service.UserService;
import com.example.myapplication.tools.DBOpenHelper;
import com.example.myapplication.tools.MD5Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneEditText;
    private EditText passwordEditText;
    private String userId;
    private User.Id id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login);
        TextView button_phone =findViewById(R.id.button_phone);
        TextView button_pw1 =findViewById(R.id.button_pw);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        button_phone.setTypeface(font);
        button_pw1.setTypeface(font);
        phoneEditText = findViewById(R.id.phone_num);
        passwordEditText = findViewById(R.id.password);
        final TextView signupText = findViewById(R.id.signup);
        final TextView adminText = findViewById(R.id.admin);
        final Button loginButton = findViewById(R.id.login);
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        adminText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }else {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                            .build();
                    UserService request = retrofit.create(UserService.class);
                    Call<UserData> call = request.getUser(phone,password);
                    call.enqueue(new Callback<UserData>() {
                        @Override
                        public void onResponse(Call<UserData> call, Response<UserData> response) {
                            System.out.println("连接成功");
                            if(response.body().getStatus()==1){
                                User user=response.body().getData();
                                userId=user.getPhone();
                                id=user.getId();
                                Intent data=new Intent();
                                if(id== User.Id.land_owner){
                                    Toast.makeText(LoginActivity.this, "农场主登录成功", Toast.LENGTH_SHORT).show();
                                    data.setClass(LoginActivity.this, User1MainActivity.class);
                                    data.putExtra("userId", phone);
                                    System.out.println(phone);
                                    setResult(RESULT_OK,data);
                                    saveLoginStatus(true,phone);
                                    LoginActivity.this.finish();
                                    startActivity(data);
                                } else if(id== User.Id.land_tenant){
                                    Toast.makeText(LoginActivity.this, "租用土地会员登录成功", Toast.LENGTH_SHORT).show();
                                    data.setClass(LoginActivity.this, User2MainActivity.class);
                                    data.putExtra("userId", phone);
                                    setResult(RESULT_OK,data);
                                    saveLoginStatus(true,phone);
                                    LoginActivity.this.finish();
                                    startActivity(data);
                                }else if(id== User.Id.consumer){
                                    Toast.makeText(LoginActivity.this, "农产品买家登录成功", Toast.LENGTH_SHORT).show();
                                    data.setClass(LoginActivity.this, User3MainActivity.class);
                                    data.putExtra("userId", phone);
                                    setResult(RESULT_OK,data);
                                    saveLoginStatus(true,phone);
                                    LoginActivity.this.finish();
                                    startActivity(data);
                                }
                            }else{
                                Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserData> call, Throwable throwable) {
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
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin", status);
        editor.putString("loginUserName", phone);
        editor.apply();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String phone=data.getStringExtra("phone");
            if(!TextUtils.isEmpty(phone)){
                //设置用户名到 et_user_name 控件
                phoneEditText.setText(phone);
                //et_user_name控件的setSelection()方法来设置光标位置
                phoneEditText.setSelection(phone.length());
            }
        }
    }
}
