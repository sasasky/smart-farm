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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.User2ListAdapter;
import com.example.myapplication.AddLogisticsActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.list;

import java.util.ArrayList;
import java.util.List;

public class AllListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.user2_list,container,false);
        final Context context=getActivity();
        RecyclerView rv = view.findViewById(R.id.recyclerView);
        List<list> lists = new ArrayList();
        lists.add(new list("2020-02-20","待发货","新鲜玉米 甜玉米 爆浆玉米 8斤装","￥8000.00","x2","16000.00",R.drawable.carrot));
        lists.add(new list("2020-02-20","已发货","新鲜玉米 甜玉米 爆浆玉米 8斤装","￥8000.00","x2","16000.00",R.drawable.carrot));
        User2ListAdapter mAdapter=new User2ListAdapter(lists);
        mAdapter.setOnItemClickListener(new User2ListAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(context, AddLogisticsActivity.class);
                context.startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
        return view;
    }
}
