package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.fragment.AddFragment;
import com.example.myapplication.fragment.CheckFragment;
import com.example.myapplication.fragment.GoodsFragment;
import com.example.myapplication.fragment.ListsFragment;
import com.example.myapplication.fragment.ReportFragment;
import com.example.myapplication.fragment.User1MainFragment;
import com.example.myapplication.fragment.User1MyFragment;

public class AdminMainActivity extends FragmentActivity implements View.OnClickListener {
    private TextView bottom_bar_text_1;
    private TextView bottom_bar_text_2;
    private TextView bottom_bar_image_1;
    private TextView bottom_bar_image_2;
    private TextView tv_main_title;
    private RelativeLayout bottom_bar_1_btn;
    private RelativeLayout bottom_bar_2_btn;
    private ReportFragment fg1;
    private CheckFragment fg2;
    private FragmentManager fragmentManager;
    //实例化
    private void initView(){
        bottom_bar_text_1=findViewById(R.id.bottom_bar_text_1);
        bottom_bar_text_2=findViewById(R.id.bottom_bar_text_2);
        bottom_bar_image_1 =findViewById(R.id.bottom_bar_image_1);
        bottom_bar_image_2 =findViewById(R.id.bottom_bar_image_2);
        tv_main_title =findViewById(R.id.text_title);
        bottom_bar_1_btn=findViewById(R.id.bottom_bar_1_btn);
        bottom_bar_2_btn=findViewById(R.id.bottom_bar_2_btn);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_admin_main);
        fragmentManager = getSupportFragmentManager();
        initView();
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        bottom_bar_image_1.setTypeface(font);
        bottom_bar_image_2.setTypeface(font);
        bottom_bar_1_btn.setOnClickListener(this);
        bottom_bar_2_btn.setOnClickListener(this);
        setSelectStatus(0);
    }

    private void setSelectStatus(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (index){
            case 0:
                bottom_bar_image_1.setText(R.string.report2);
                bottom_bar_image_2.setText(R.string.land1);
                bottom_bar_image_1.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#666666"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#666666"));
                tv_main_title.setText("举报信息");
                if (fg1 == null) {
                    fg1 = new ReportFragment();
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
                    fragmentTransaction.show(fg1);
                }
                fragmentTransaction.commit();
                break;
            case 1:
                bottom_bar_image_1.setText(R.string.report1);
                bottom_bar_image_2.setText(R.string.land2);
                bottom_bar_image_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_1.setTextColor(Color.parseColor("#666666"));
                bottom_bar_text_2.setTextColor(Color.parseColor("#4CAF50"));
                bottom_bar_image_2.setTextColor(Color.parseColor("#4CAF50"));
                tv_main_title.setText("审核土地");
                if (fg2 == null) {
                    fg2 = new CheckFragment();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
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

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_bar_1_btn:
                setSelectStatus(0);
                break;
            case R.id.bottom_bar_2_btn:
                setSelectStatus(1);
                break;
        }
    }
}
