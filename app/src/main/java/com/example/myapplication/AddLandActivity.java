package com.example.myapplication;

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
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.SDKInitializer;
import com.example.myapplication.entity.LandPostdata;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.service.LandService;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddLandActivity extends AppCompatActivity {
    private String title ;
    private String detail ;
    private String price;
    private String area;
    private int landId;
    private String photoUrl;
    private Button camera;
    private Button pic;
    private Button cancel;
    private Dialog dialog;
    private View inflate;
    private ImageView good_image;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA };
    private File tempFile = null;
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        userId = i.getStringExtra("userId");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_add_land);
        TextView back = findViewById(R.id.button_backward);
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        back.setTypeface(font);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLandActivity.this.finish();
            }
        });
        final EditText edt_title = findViewById(R.id.edt_title);
        final EditText edt_detail = findViewById(R.id.edt_detail);
        final EditText edt_price = findViewById(R.id.edt_price);
        final EditText edt_area = findViewById(R.id.edt_area);
        Button add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_title.getText().toString();
                detail = edt_detail.getText().toString();
                price = edt_price.getText().toString();
                area = edt_area.getText().toString();
                if(TextUtils.isEmpty(title)){
                    Toast.makeText(AddLandActivity.this, "请输入土地名称", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(detail)){
                    Toast.makeText(AddLandActivity.this, "请输入土地详情", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(price)){
                    Toast.makeText(AddLandActivity.this, "请输入土地租金", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(area)){
                    Toast.makeText(AddLandActivity.this, "请输入土地面积", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddLandActivity.this);//设置弹出框的第二种方法
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

        ImageButton addPic = findViewById(R.id.button_image);
        good_image = findViewById(R.id.good_image);
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show(view);
                requestPic();
            }
        });
    }

    public void requestAdd() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LandService request = retrofit.create(LandService.class);
        Call<LandPostdata> call = request.postResult(userId,title,Double.parseDouble(price),detail,Double.parseDouble(area));
        call.enqueue(new Callback<LandPostdata>() {
            @Override
            public void onResponse(Call<LandPostdata> call, Response<LandPostdata> response) {
                System.out.println("连接成功");
                if(response.body().getStatus()==1){
                    landId = response.body().getLandId();
                    Toast.makeText(AddLandActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                }
                System.out.println(response.body().getMsg());
            }
            @Override
            public void onFailure(Call<LandPostdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public void requestPic() {
        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        LandService request = retrofit.create(LandService.class);
        Call<LandPostdata> call = request.imgResult(photoUrl,landId);
        call.enqueue(new Callback<LandPostdata>() {
            @Override
            public void onResponse(Call<LandPostdata> call, Response<LandPostdata> response) {
                System.out.println("连接成功");
                System.out.println(response.body().getMsg());
            }
            @Override
            public void onFailure(Call<LandPostdata> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
    }

    public int verifyPermissions(Context context,String permission) {
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
        pic = inflate.findViewById(R.id.btn_pop_album);
        camera = inflate.findViewById(R.id.btn_pop_camera);
        cancel = inflate.findViewById(R.id.btn_pop_cancel);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //检查是否已经获得相机的权限
                if(verifyPermissions(AddLandActivity.this,PERMISSIONS_STORAGE[2]) == 0){
                    System.out.println("提示是否要授权");
                    ActivityCompat.requestPermissions(AddLandActivity.this, PERMISSIONS_STORAGE, 3);
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
                        good_image.setImageBitmap(bitmap);
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
                        good_image.setImageBitmap(bitmap);
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
}