package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.User2GoodAdapter;
import com.example.myapplication.GoodsDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.goods;

import java.util.ArrayList;
import java.util.List;

public class User2OtherFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user2_good,container,false);
        final Context context=getActivity();
        RecyclerView rv = view.findViewById(R.id.recyclerView);
        final List<goods> good = new ArrayList();
        good.add(new goods("新鲜玉米 甜玉米 爆浆玉米 8斤装","￥8000.00","20人已购买",R.drawable.carrot));
        final User2GoodAdapter mAdapter=new User2GoodAdapter(good);
        mAdapter.setOnItemClickListener(new User2GoodAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(context, GoodsDetailActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {
            }
        });
        rv.setLayoutManager(new GridLayoutManager(context,2));
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        return view;
    }
}
