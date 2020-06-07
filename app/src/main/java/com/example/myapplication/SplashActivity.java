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

public class SplashActivity extends Activity {
    private Intent intent;
    private SharedPreferences sp;
    // private final int SPLASH_DISPLAY_LENGHT = 2000; // 两秒后进入系统
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        //getSupportActionBar().hide();//隐藏标题栏
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        final boolean isLogin = sp.getBoolean("isLogin", false);
        String userId = sp.getString("loginUserName", null);
        final String id = sp.getString("id", null);
        Thread myThread=new Thread(){//创建子线程
            @Override
            public void run() {
                try{
                    sleep(2000);//使程序休眠2秒
                    intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();//关闭当前活动
//                   // intent = new Intent(getApplicationContext(),LoginActivity.class);
//                    if(isLogin){
//                        assert id != null;
//                        switch (id) {
//                            case "land_owner":
//                                intent = new Intent(getApplicationContext(), User1MainActivity.class);
//                                startActivity(intent);
//                                finish();//关闭当前活动
//                                break;
//                            case "land_tenant":
//                                intent = new Intent(getApplicationContext(), User2MainActivity.class);
//                                startActivity(intent);
//                                finish();//关闭当前活动
//                                break;
//                            case "consumer":
//                                intent = new Intent(getApplicationContext(), User3MainActivity.class);
//                                startActivity(intent);
//                                finish();//关闭当前活动
//                                break;
//                        }
//                    }
//                    else {
//                        intent = new Intent(getApplicationContext(),LoginActivity.class);
//                        startActivity(intent);
//                        finish();//关闭当前活动
//                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        myThread.start();//启动线程
    }
}
