package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.fragment.AddFragment;
import com.example.myapplication.fragment.GoodsFragment;
import com.example.myapplication.fragment.ListsFragment;
import com.example.myapplication.fragment.User1MainFragment;
import com.example.myapplication.fragment.User1MyFragment;

public class User2MainActivity extends FragmentActivity implements View.OnClickListener {
    private TextView bottom_bar_text_1;
    private TextView bottom_bar_text_2;
    private TextView bottom_bar_text_4;
    private TextView bottom_bar_text_5;
    private TextView bottom_bar_image_1;
    private TextView bottom_bar_image_2;
    private TextView bottom_bar_image_3;
    private TextView bottom_bar_image_4;
    private TextView bottom_bar_image_5;
    private TextView tv_main_title;
    private LinearLayout main_bottom_bar;
    private RelativeLayout bottom_bar_1_btn;
    private RelativeLayout bottom_bar_2_btn;
    private RelativeLayout bottom_bar_3_btn;
    private RelativeLayout bottom_bar_4_btn;
    private RelativeLayout bottom_bar_5_btn;
    private User1MainFragment fg1;
    private AddFragment fg2;
    private User1MyFragment fg3;
    private GoodsFragment fg4;
    private ListsFragment fg5;
    private FragmentManager fragmentManager;
    private String userId;
    //实例化
    private void initView(){
        bottom_bar_text_1=findViewById(R.id.bottom_bar_text_1);
        bottom_bar_text_2=findViewById(R.id.bottom_bar_text_2);
        bottom_bar_text_4=findViewById(R.id.bottom_bar_text_4);
        bottom_bar_text_5=findViewById(R.id.bottom_bar_text_5);
        bottom_bar_image_1 =findViewById(R.id.bottom_bar_image_1);
        bottom_bar_image_2 =findViewById(R.id.bottom_bar_image_2);
        bottom_bar_image_3 =findViewById(R.id.bottom_bar_image_3);
        bottom_bar_image_4 =findViewById(R.id.bottom_bar_image_4);
        bottom_bar_image_5 =findViewById(R.id.bottom_bar_image_5);
        tv_main_title =findViewById(R.id.text_title);
        main_bottom_bar=findViewById(R.id.main_bottom_bar);
        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_3_btn=findViewById(R.id.bottom_bar_3_btn);
        bottom_bar_4_btn=findViewById(R.id.bottom_bar_4_btn);
        bottom_bar_5_btn=findViewById(R.id.bottom_bar_5_btn);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        userId = i.getStringExtra("userId");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user2_main);
        fragmentManager = getSupportFragmentManager();
        initView();
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        bottom_bar_image_1.setTypeface(font);
        bottom_bar_image_2.setTypeface(font);
        bottom_bar_image_3.setTypeface(font);
        bottom_bar_image_4.setTypeface(font);
        bottom_bar_image_5.setTypeface(font);
        bottom_bar_1_btn.setOnClickListener(this);
        bottom_bar_2_btn.setOnClickListener(this);
        bottom_bar_3_btn.setOnClickListener(this);
        bottom_bar_4_btn.setOnClickListener(this);
        bottom_bar_5_btn.setOnClickListener(this);
        TextView setting =findViewById(R.id.button_setting);
        setting.setTypeface(font);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(User2MainActivity.this, SettingActivity.class);//启动MainActivity
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
        setSelectStatus(0);
    }

    private void setSelectStatus(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (index){
            case 0:
                bottom_bar_image_1.setText(R.string.home2);
                bottom_bar_image_2.setText(R.string.land1);
                bottom_bar_image_4.setText(R.string.goods1);
                bottom_bar_image_5.setText(R.string.list1);
                bottom_bar_image_1.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_3.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_4.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_4.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_5.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_5.setTextColor(Color.parseColor("#666666"));
                tv_main_title.setText("租地");
                if (fg1 == null) {
                    fg1 = new User1MainFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userId",userId);
                    fg1.setArguments(bundle);
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
                    fragmentTransaction.show(fg1);
                }
                fragmentTransaction.commit();
                break;
            case 1:
                bottom_bar_image_1.setText(R.string.home1);
                bottom_bar_image_2.setText(R.string.land2);
                bottom_bar_image_4.setText(R.string.goods1);
                bottom_bar_image_5.setText(R.string.list1);
                bottom_bar_image_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_3.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_4.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_4.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_5.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_5.setTextColor(Color.parseColor("#666666"));
                tv_main_title.setText("我的土地");
                if (fg3 == null) {
                    fg3 = new User1MyFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userId",userId);
                    fg3.setArguments(bundle);
                    fragmentTransaction.add(R.id.content, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                fragmentTransaction.commit();
                break;
            case 2:
                bottom_bar_image_1.setText(R.string.home1);
                bottom_bar_image_2.setText(R.string.land1);
                bottom_bar_image_4.setText(R.string.goods1);
                bottom_bar_image_5.setText(R.string.list1);
                bottom_bar_image_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_3.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_4.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_4.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_5.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_5.setTextColor(Color.parseColor("#666666"));
                tv_main_title.setText("发布农产品");
                if (fg2 == null) {
                    fg2 = new AddFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userId",userId);
                    fg2.setArguments(bundle);
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                fragmentTransaction.commit();
                break;
            case 3:
                bottom_bar_image_1.setText(R.string.home1);
                bottom_bar_image_2.setText(R.string.land1);
                bottom_bar_image_4.setText(R.string.goods2);
                bottom_bar_image_5.setText(R.string.list1);
                bottom_bar_image_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_3.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_4.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_image_4.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_5.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_5.setTextColor(Color.parseColor("#666666"));
                tv_main_title.setText("我的商品");
                if (fg4 == null) {
                    fg4 = new GoodsFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userId",userId);
                    fg4.setArguments(bundle);
                    fragmentTransaction.add(R.id.content, fg4);
                } else {
                    fragmentTransaction.show(fg4);
                }
                fragmentTransaction.commit();
                break;
            case 4:
                bottom_bar_image_1.setText(R.string.home1);
                bottom_bar_image_2.setText(R.string.land1);
                bottom_bar_image_4.setText(R.string.goods1);
                bottom_bar_image_5.setText(R.string.list2);
                bottom_bar_image_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_3.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_4.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_4.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_5.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_image_5.setTextColor(Color.parseColor("#4CAF50"));
                tv_main_title.setText("我的订单");
                if (fg5 == null) {
                    fg5 = new ListsFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("userId",userId);
                    fg5.setArguments(bundle);
                    fragmentTransaction.add(R.id.content, fg5);
                } else {
                    fragmentTransaction.show(fg5);
                }
                fragmentTransaction.commit();
                break;
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (fg1 != null) {
            transaction.hide(fg1);
        }
        if (fg2 != null) {
            transaction.hide(fg2);
        }
        if (fg3 != null) {
            transaction.hide(fg3);
        }
        if (fg4 != null) {
            transaction.hide(fg4);
        }
        if (fg5 != null) {
            transaction.hide(fg5);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_bar_1_btn:
                setSelectStatus(0);
                break;
            case R.id.bottom_bar_2_btn:
                setSelectStatus(1);
                break;
            case R.id.bottom_bar_3_btn:
                setSelectStatus(2);
                break;
            case R.id.bottom_bar_4_btn:
                setSelectStatus(3);
                break;
            case R.id.bottom_bar_5_btn:
                setSelectStatus(4);
                break;
        }
    }
}
