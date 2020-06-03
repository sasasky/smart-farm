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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.service.UserService;
import com.example.myapplication.tools.DBOpenHelper;
import com.example.myapplication.tools.MD5Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity{
    private DBOpenHelper mDBOpenHelper;
    private User.Id id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_sign_up);
        TextView button_name =findViewById(R.id.button_name);
        TextView button_phone =findViewById(R.id.button_phone);
        TextView button_pw1 =findViewById(R.id.button_pw1);
        TextView button_pw2 =findViewById(R.id.button_pw2);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        button_name.setTypeface(font);
        button_phone.setTypeface(font);
        button_pw1.setTypeface(font);
        button_pw2.setTypeface(font);
        mDBOpenHelper = new DBOpenHelper(this);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText phoneEditText = findViewById(R.id.phone_num);
        final EditText passwordEditText = findViewById(R.id.password);
        final EditText passwordAgainEditText = findViewById(R.id.password_again);
        final EditText numberEditText = findViewById(R.id.number);
        final Button verificationButton = findViewById(R.id.verification_button);
        final Button signupButton = findViewById(R.id.signup);
        final RadioGroup radioGroup_user = findViewById(R.id.user_status);
        final RadioButton user1= findViewById(R.id.user1);
        final RadioButton user2= findViewById(R.id.user2);
        final RadioButton user3= findViewById(R.id.user3);
        radioGroup_user.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (user1.getId() == checkedId) {
                    id= User.Id.land_owner;
                }
                if (user2.getId() == checkedId) {
                    id= User.Id.land_tenant;
                }
                if (user3.getId() == checkedId) {
                    id= User.Id.consumer;
                }
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String password1 = passwordEditText.getText().toString().trim();
                String password2 = passwordAgainEditText.getText().toString().trim();
                String number = numberEditText.getText().toString().trim();
                //注册验证
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(SignUpActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(SignUpActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(number)){
                    Toast.makeText(SignUpActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password1)){
                    Toast.makeText(SignUpActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(password2)){
                    Toast.makeText(SignUpActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                }else if(!password1.equals(password2)){
                    Toast.makeText(SignUpActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }else {
                    //将用户名和密码加入到数据库中
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                            .build();
                    UserService request = retrofit.create(UserService.class);
                    Call<UserData> call = request.postResult(phone,password1,id);
                    call.enqueue(new Callback<UserData>() {
                        @Override
                        public void onResponse(Call<UserData> call, Response<UserData> response) {
                            if(response.body().getStatus()==1){
                                Toast.makeText(SignUpActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences=getSharedPreferences("config", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=sharedPreferences.edit();
                                editor.putBoolean("isLogin", true);
                                editor.putString("loginUserName",response.body().getData().getPhone());
                                editor.apply();
                            }else{
                                Toast.makeText(SignUpActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserData> call, Throwable throwable) {
                            System.out.println("连接失败");
                            System.out.println(throwable.getMessage());
                        }
                    });
                    Intent data = new Intent();
                    data.putExtra("phone", phone);
                    setResult(RESULT_OK, data);
                    switch (id){
                        case land_owner:
                            Toast.makeText(SignUpActivity.this, "农场主身份注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(SignUpActivity.this, User1MainActivity.class);
                            startActivity(intent1);
                            finish();
                            break;
                        case land_tenant:
                            Toast.makeText(SignUpActivity.this, "租用土地会员身份注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(SignUpActivity.this, User2MainActivity.class);
                            startActivity(intent2);
                            finish();
                            break;
                        case consumer:
                            Toast.makeText(SignUpActivity.this, "农产品买家身份注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent3 = new Intent(SignUpActivity.this, User3MainActivity.class);
                            startActivity(intent3);
                            finish();
                            break;
                    }
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

    private boolean isExistPhone(String phone){
        boolean has_userName=false;
        //mode_private SharedPreferences sp = getSharedPreferences( );
        // "loginInfo", MODE_PRIVATE
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取密码
        String spPsw=sp.getString(phone, "");//传入用户名获取密码
        //如果密码不为空则确实保存过这个用户名
        if(!TextUtils.isEmpty(spPsw)) {
            has_userName=true;
        }
        return has_userName;
    }

    private void saveRegisterInfo(String phone,String psw){
        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码）;
        editor.putString(phone, md5Psw);
        //提交修改 editor.commit();
        editor.commit();
    }
}
