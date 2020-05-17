package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.fragment.AddFragment;
import com.example.myapplication.fragment.CartFragment;
import com.example.myapplication.fragment.GoodsFragment;
import com.example.myapplication.fragment.ListsFragment;
import com.example.myapplication.fragment.User1MainFragment;
import com.example.myapplication.fragment.User1MyFragment;
import com.example.myapplication.fragment.User2MainFragment;
import com.example.myapplication.fragment.User2MyFragment;

public class User3MainActivity extends FragmentActivity implements View.OnClickListener{
    private TextView bottom_bar_text_1;
    private TextView bottom_bar_text_2;
    private TextView bottom_bar_text_3;
    private TextView bottom_bar_image_1;
    private TextView bottom_bar_image_2;
    private TextView bottom_bar_image_3;
    private TextView tv_main_title;
    private LinearLayout main_bottom_bar;
    private RelativeLayout bottom_bar_1_btn;
    private RelativeLayout bottom_bar_2_btn;
    private RelativeLayout bottom_bar_3_btn;
    private User2MainFragment fg1;
    private CartFragment fg2;
    private User2MyFragment fg3;
    private FragmentManager fragmentManager;
    private void initView(){
        bottom_bar_text_1=findViewById(R.id.bottom_bar_text_1);
        bottom_bar_text_2=findViewById(R.id.bottom_bar_text_2);
        bottom_bar_text_3=findViewById(R.id.bottom_bar_text_3);
        bottom_bar_image_1 =findViewById(R.id.bottom_bar_image_1);
        bottom_bar_image_2 =findViewById(R.id.bottom_bar_image_2);
        bottom_bar_image_3 =findViewById(R.id.bottom_bar_image_3);
        tv_main_title =findViewById(R.id.text_title);
        main_bottom_bar=findViewById(R.id.main_bottom_bar);
        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
        bottom_bar_3_btn=findViewById(R.id.bottom_bar_3_btn);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user3_main);
        fragmentManager = getSupportFragmentManager();
        initView();
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        TextView search =findViewById(R.id.button_search);
        search.setTypeface(font);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(User3MainActivity.this, SearchActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        bottom_bar_image_1.setTypeface(font);
        bottom_bar_image_2.setTypeface(font);
        bottom_bar_image_3.setTypeface(font);
        bottom_bar_1_btn.setOnClickListener(this);
        bottom_bar_2_btn.setOnClickListener(this);
        bottom_bar_3_btn.setOnClickListener(this);
        setSelectStatus(0);
    }

    private void setSelectStatus(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (index){
            case 0:
                bottom_bar_image_1.setText(R.string.home2);
                bottom_bar_image_2.setText(R.string.cart1);
                bottom_bar_image_3.setText(R.string.my1);
                bottom_bar_image_1.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_3.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_3.setTextColor(Color.parseColor("#666666"));
                tv_main_title.setText("商城");
                if (fg1 == null) {
                    fg1 = new User2MainFragment();
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
                    fragmentTransaction.show(fg1);
                }
                fragmentTransaction.commit();
                break;
            case 1:
                bottom_bar_image_1.setText(R.string.home1);
                bottom_bar_image_2.setText(R.string.cart2);
                bottom_bar_image_3.setText(R.string.my1);
                bottom_bar_image_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_3.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_3.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#4CAF50"));
                tv_main_title.setText("我的购物车");
                if (fg2 == null) {
                    fg2 = new CartFragment();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                fragmentTransaction.commit();
                break;
            case 2:
                bottom_bar_image_1.setText(R.string.home1);
                bottom_bar_image_2.setText(R.string.cart1);
                bottom_bar_image_3.setText(R.string.my2);
                bottom_bar_image_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_3.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_3.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#666666"));
                tv_main_title.setText("我的");
                if (fg3 == null) {
                    fg3 = new User2MyFragment();
                    fragmentTransaction.add(R.id.content, fg3);
                } else {
                    fragmentTransaction.show(fg3);
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
        }
    }
}
