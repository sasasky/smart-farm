package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.entity.goodData;
import com.example.myapplication.entity.goods;
import com.example.myapplication.service.GoodService;
import com.example.myapplication.service.UserService;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends AppCompatActivity {
    private String userId;
    private String name;
    private String old_pwd;
    private String new_pwd;
    private String photoUrl;
    private Dialog dialog;
    private View inflate;
    private ImageView imageView;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA };
    private File tempFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        Intent i = getIntent();
        userId = i.getStringExtra("userId");
        setContentView(R.layout.activity_setting);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        TextView back =findViewById(R.id.button_backward);
        TextView off =findViewById(R.id.button_off);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        off.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);//设置弹出框的第二种方法
                builder.setTitle("退出登录");
                builder.setMessage("确定退出登录吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(SettingActivity.this, LoginActivity.class);//启动MainActivity
                        startActivity(it);
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });
        imageView =findViewById(R.id.imageView);
        final EditText edit_name =findViewById(R.id.edit_name);
        final EditText edit_old =findViewById(R.id.edit_old);
        final EditText edit_new =findViewById(R.id.edit_new);
        name = edit_name.getText().toString();
        old_pwd = edit_old.getText().toString();
        new_pwd = edit_new.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        final UserService request = retrofit.create(UserService.class);
        Call<UserData> call = request.getInfo(userId);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                User userinfo=response.body().getData();
                edit_name.setText(userinfo.getName());
                imageView.setImageBitmap(userinfo.getPhoto());
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });

        Button change_pwd = findViewById(R.id.change_pwd);
        change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);//设置弹出框的第二种方法
                builder.setTitle("修改");
                builder.setMessage("确定修改吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request1();
                        Intent it = new Intent(SettingActivity.this, User1MainActivity.class);//启动MainActivity
                        startActivity(it);
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });

        Button change = findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);//设置弹出框的第二种方法
                builder.setTitle("修改");
                builder.setMessage("确定修改吗?");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request2();
                        Intent it = new Intent(SettingActivity.this, User1MainActivity.class);//启动MainActivity
                        startActivity(it);
                    }
                });
                builder.setNegativeButton("否", null);
                builder.show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                        .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                        .build();
                final UserService request = retrofit.create(UserService.class);
                Call<UserData> call = request.changePic(userId,photoUrl);
                call.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        System.out.println("连接成功");
                        System.out.println(response.message()+"   "+response.body());
                    }
                    @Override
                    public void onFailure(Call<UserData> call, Throwable throwable) {
                        System.out.println("连接失败");
                        System.out.println(throwable.getMessage());
                    }
                });
            }
        });
    }

    public int verifyPermissions(Context context, String permission) {
        int Permission = ActivityCompat.checkSelfPermission(context,permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {
            System.out.println("已经同意权限");
            return 1;
        }else{
            System.out.println("没有同意权限");
            return 0;
        }
    }

    public void show(View view){
        dialog = new Dialog(this,R.style.DialogTheme);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.select, null);
        //初始化控件
        Button pic = inflate.findViewById(R.id.btn_pop_album);
        Button camera = inflate.findViewById(R.id.btn_pop_camera);
        Button cancel = inflate.findViewById(R.id.btn_pop_cancel);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检查是否已经获得相机的权限
                if(verifyPermissions(SettingActivity.this,PERMISSIONS_STORAGE[2]) == 0){
                    System.out.println("提示是否要授权");
                    ActivityCompat.requestPermissions(SettingActivity.this, PERMISSIONS_STORAGE, 3);
                }else{
                    //已经有权限
                    toCamera();  //打开相机
                }
            }
        });
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPicture();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断返回码不等于0
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 0) {
            //读取返回码
            switch (requestCode) {
                case 100:   //相册返回的数据（相册的返回码）
                    System.out.println("相册");
                    Uri uri01 = data.getData();
                    assert uri01 != null;
                    photoUrl = uri01.toString();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri01));
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 101:  //相机返回的数据（相机的返回码）
                    System.out.println("相机");
                    try {
                        tempFile = new File(Environment.getExternalStorageDirectory(), "fileImg.jpg");  //相机取图片数据文件
                        Uri uri02 = Uri.fromFile(tempFile);   //图片文件
                        assert uri02 != null;
                        photoUrl = uri02.toString();
                        Bitmap bitmap = BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri02));
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent,100);
        System.out.println("跳转相册成功");
    }
    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //跳转到 ACTION_IMAGE_CAPTURE
        //判断内存卡是否可用，可用的话就进行存储
        //putExtra：取值，Uri.fromFile：传一个拍照所得到的文件，fileImg.jpg：文件名
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fileImg.jpg")));
        startActivityForResult(intent,101); // 101: 相机的返回码参数（随便一个值就行，只要不冲突就好）
        System.out.println("跳转相机成功");
    }

    public void request1() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        UserService request = retrofit.create(UserService.class);
        Call<UserData> call = request.changepwd("userId",old_pwd,new_pwd);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                System.out.println("连接成功");
                System.out.println(response.message()+"   "+response.body());
                Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void request2() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        UserService request = retrofit.create(UserService.class);
        Call<UserData> call = request.changeInfo("userId",name);
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                System.out.println("连接成功");
                System.out.println(response.message()+"   "+response.body());
                Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }
}
