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
import com.example.myapplication.ChangeGoodActivity;
import com.example.myapplication.R;
import com.example.myapplication.entity.goods;
import com.hb.dialog.myDialog.MyAlertDialog;

import java.util.ArrayList;
import java.util.List;

public class User1OtherFragment extends Fragment {
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
                intent.setClass(context, ChangeGoodActivity.class);
                context.startActivity(intent);
            }
            @Override
            public void onItemLongClick(final int position) {
                MyAlertDialog myAlertDialog = new MyAlertDialog (context).builder()
                        .setTitle("确认吗？")
                        .setMsg("删除内容")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                good.remove(position);
                                mAdapter.notifyItemRemoved(position);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        });
                myAlertDialog.show();
            }
        });
        rv.setLayoutManager(new GridLayoutManager(context,2));
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        return view;
    }
}
