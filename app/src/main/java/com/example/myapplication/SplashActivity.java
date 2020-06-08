package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.fragment.User1MainFragment;
import com.example.myapplication.service.UserService;
import com.example.myapplication.tools.SharePreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends Activity {
    private boolean isLogin;
    private Intent intent;
    private SharedPreferences sp;
    // private final int SPLASH_DISPLAY_LENGHT = 2000; // 两秒后进入系统
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_splash);
        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(2000);//使程序休眠2秒
                    intent = new Intent(getApplicationContext(),LoginActivity.class);
//                    sp = getSharedPreferences("config", Context.MODE_PRIVATE);
//                    boolean isLogin = sp.getBoolean("isLogin", false);
//                    String userId = sp.getString("loginUserName", null);
//                    final User.Id[] id = new User.Id[1];
//                    Retrofit retrofit = new Retrofit.Builder()
//                            .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
//                            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
//                            .build();
//                    final UserService request = retrofit.create(UserService.class);
//                    Call<UserData> call = request.getInfo(userId);
//                    call.enqueue(new Callback<UserData>() {
//                        @Override
//                        public void onResponse(Call<UserData> call, Response<UserData> response) {
//                            User userinfo=response.body().getData();
//                            id[0] =userinfo.getId();
//                        }
//                        @Override
//                        public void onFailure(Call<UserData> call, Throwable throwable) {
//                            System.out.println("连接失败");
//                            System.out.println(throwable.getMessage());
//                        }
//                    });
//                    if(isLogin){
//                        if(id[0]==User.Id.land_owner){
//                            intent = new Intent(getApplicationContext(),User1MainActivity.class);
//                        }else if(id[0]==User.Id.land_tenant){
//                            intent = new Intent(getApplicationContext(),User2MainActivity.class);
//                        }else if(id[0]==User.Id.consumer){
//                            intent = new Intent(getApplicationContext(),User3MainActivity.class);
//                        }
//                    }
//                    else {
//                        intent = new Intent(getApplicationContext(),LoginActivity.class);
//                    }
                    startActivity(intent);
                    finish();//关闭当前活动
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}
