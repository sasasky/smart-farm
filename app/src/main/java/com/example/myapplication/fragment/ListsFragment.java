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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.User2ListAdapter;
import com.example.myapplication.AllListActivity;
import com.example.myapplication.MyActivity;
import com.example.myapplication.OrderInfoActivity;
import com.example.myapplication.OverListActivity;
import com.example.myapplication.PayLeaseActivity;
import com.example.myapplication.PayListActivity;
import com.example.myapplication.R;
import com.example.myapplication.SelfOrderActivity;
import com.example.myapplication.entity.User;
import com.example.myapplication.entity.UserData;
import com.example.myapplication.entity.order;
import com.example.myapplication.entity.orderlist;
import com.example.myapplication.service.OrderService;
import com.example.myapplication.service.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListsFragment extends Fragment {
    private String userId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2_lists, container, false);
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
        TextView set =view.findViewById(R.id.set);
        TextView set_in =view.findViewById(R.id.set_in);
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
                Intent it=new Intent(context, PayLeaseActivity.class);
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
        LinearLayout pay_list =view.findViewById(R.id.pay_list);
        pay_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, SelfOrderActivity.class);
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