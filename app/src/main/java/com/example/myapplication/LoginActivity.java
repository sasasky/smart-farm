package com.example.myapplication;

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
import com.example.myapplication.tools.DBOpenHelper;
import com.example.myapplication.tools.MD5Utils;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private EditText phoneEditText;
    private EditText passwordEditText;
    private String spPsw;//加密密码
    private User tempUser;
    private DBOpenHelper mDBOpenHelper;

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
        final Button loginButton = findViewById(R.id.login);
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String md5Psw= MD5Utils.md5(password);
                // md5Psw ; spPsw 为 根据从SharedPreferences中用户名读取密码
                // 定义方法 readPsw为了读取用户名，得到密码
                spPsw=readPsw(phone);
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                    // md5Psw.equals(); 判断，输入的密码加密后，是否与保存在SharedPreferences中一致
                }else if(md5Psw.equals(spPsw)){
                    ArrayList<User> persons = mDBOpenHelper.getAllData();
                    for(User ps:persons){
                        tempUser=ps;
                        break;
                    }
                    if(1==tempUser.getId()){
                        Toast.makeText(LoginActivity.this, "农场主登录成功", Toast.LENGTH_SHORT).show();
                        saveLoginStatus(true, phone);
                        //登录成功后关闭此页面进入主页
                        Intent data=new Intent();
                        //datad.putExtra( ); name , value ;
                        data.putExtra("isLogin",true);
                        //RESULT_OK为Activity系统常量，状态码为-1
                        // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                        setResult(RESULT_OK,data);
                        //销毁登录界面
                        LoginActivity.this.finish();
                        //跳转到主界面，登录成功的状态传递到 MainActivity 中
                        startActivity(new Intent(LoginActivity.this, User1MainActivity.class));
                        //根据权限的不同跳传到不同的模块（页面）
                    } else if(2==tempUser.getId()){
                        Toast.makeText(LoginActivity.this, "租用土地会员登录成功", Toast.LENGTH_SHORT).show();
                        saveLoginStatus(true, phone);
                        //登录成功后关闭此页面进入主页
                        Intent data=new Intent();
                        //datad.putExtra( ); name , value ;
                        data.putExtra("isLogin",true);
                        //RESULT_OK为Activity系统常量，状态码为-1
                        // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                        setResult(RESULT_OK,data);
                        //销毁登录界面
                        LoginActivity.this.finish();
                        //跳转到主界面，登录成功的状态传递到 MainActivity 中
                        startActivity(new Intent(LoginActivity.this, User2MainActivity.class));
                        //根据权限的不同跳传到不同的模块（页面）
                    }else if(3==tempUser.getId()){
                        Toast.makeText(LoginActivity.this, "农产品买家登录成功", Toast.LENGTH_SHORT).show();
                        saveLoginStatus(true, phone);
                        //登录成功后关闭此页面进入主页
                        Intent data=new Intent();
                        //datad.putExtra( ); name , value ;
                        data.putExtra("isLogin",true);
                        //RESULT_OK为Activity系统常量，状态码为-1
                        // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                        setResult(RESULT_OK,data);
                        //销毁登录界面
                        LoginActivity.this.finish();
                        //跳转到主界面，登录成功的状态传递到 MainActivity 中
                        startActivity(new Intent(LoginActivity.this, User3MainActivity.class));
                        //根据权限的不同跳传到不同的模块（页面）
                    }
                    return;
                }else if((spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw))){
                    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String readPsw(String phone){
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(phone , "");
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
