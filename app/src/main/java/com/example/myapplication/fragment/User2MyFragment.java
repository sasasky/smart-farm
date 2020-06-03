package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.AllListActivity;
import com.example.myapplication.MyActivity;
import com.example.myapplication.OverListActivity;
import com.example.myapplication.PayListActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class User2MyFragment extends Fragment {
    private String userId;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user3_my, container, false);
        Bundle bundle=getArguments();
        if(bundle!=null){
            userId=bundle.getString("userId");
        }
        final Context context = getActivity();
        final ImageView pic =view.findViewById(R.id.pic);
        final TextView name =view.findViewById(R.id.name);
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
                name.setText(userinfo.getName());
                pic.setImageBitmap(userinfo.getPhoto());
            }
            @Override
            public void onFailure(Call<UserData> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });
        Typeface font = Typeface.createFromAsset(requireActivity().getAssets(), "iconfont.ttf");
        TextView all =view.findViewById(R.id.all);
        TextView all_in =view.findViewById(R.id.all_in);
        TextView pay =view.findViewById(R.id.pay);
        TextView pay_in =view.findViewById(R.id.pay_in);
        TextView over =view.findViewById(R.id.over);
        TextView over_in =view.findViewById(R.id.over_in);
        TextView set =view.findViewById(R.id.set);
        TextView set_in =view.findViewById(R.id.set_in);
        over_in.setTypeface(font);
        over.setTypeface(font);
        pay_in.setTypeface(font);
        pay.setTypeface(font);
        all_in.setTypeface(font);
        all.setTypeface(font);
        set.setTypeface(font);
        set_in.setTypeface(font);
        LinearLayout all_list =view.findViewById(R.id.all_list);
        all_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, AllListActivity.class);
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
        LinearLayout pay_list =view.findViewById(R.id.pay_list);
        pay_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, PayListActivity.class);
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
        LinearLayout over_list =view.findViewById(R.id.over_list);
        over_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, OverListActivity.class);
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
        LinearLayout setting =view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, MyActivity.class);
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
        return view;
    }
}
