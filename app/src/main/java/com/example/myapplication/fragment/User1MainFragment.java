package com.example.myapplication.fragment;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.User2LandAdapter;
import com.example.myapplication.ChangeLandActivity;
import com.example.myapplication.LandDataActivity;
import com.example.myapplication.LandDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.land;

import java.util.ArrayList;
import java.util.List;

public class User1MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2_main, container, false);
        final Context context=getActivity();
        RecyclerView rv = view.findViewById(R.id.recyclerView);
        List<land> landList = new ArrayList();
        //landList.add(new land("有机花生种植基地","300元起","农场种植有机花生种植基地预售",R.drawable.land));
        //landList.add(new land("有机花生种植基地","300元起","农场种植有机花生种植基地预售",R.drawable.land));
        //landList.add(new land("有机花生种植基地","300元起","农场种植有机花生种植基地预售",R.drawable.land));
        User2LandAdapter mAdapter=new User2LandAdapter(landList);
        mAdapter.setOnItemClickListener(new User2LandAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent();
                intent.setClass(context, LandDetailActivity.class);
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