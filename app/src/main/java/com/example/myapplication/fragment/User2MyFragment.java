package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.AllListActivity;
import com.example.myapplication.ChangeGoodActivity;
import com.example.myapplication.MyActivity;
import com.example.myapplication.OverListActivity;
import com.example.myapplication.PayListActivity;
import com.example.myapplication.R;
import com.example.myapplication.User2MainActivity;
import com.example.myapplication.WayListActivity;

public class User2MyFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user3_my, container, false);
        final Context context = getActivity();
        Typeface font = Typeface.createFromAsset(requireActivity().getAssets(), "iconfont.ttf");
        TextView all =view.findViewById(R.id.all);
        TextView all_in =view.findViewById(R.id.all_in);
        TextView pay =view.findViewById(R.id.pay);
        TextView pay_in =view.findViewById(R.id.pay_in);
        TextView way =view.findViewById(R.id.way);
        TextView way_in =view.findViewById(R.id.way_in);
        TextView over =view.findViewById(R.id.over);
        TextView over_in =view.findViewById(R.id.over_in);
        TextView set =view.findViewById(R.id.set);
        TextView set_in =view.findViewById(R.id.set_in);
        way_in.setTypeface(font);
        way.setTypeface(font);
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
                Intent it=new Intent(context, AllListActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        LinearLayout pay_list =view.findViewById(R.id.pay_list);
        pay_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, PayListActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        LinearLayout way_list =view.findViewById(R.id.way_list);
        way_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, WayListActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        LinearLayout over_list =view.findViewById(R.id.over_list);
        over_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, OverListActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        LinearLayout setting =view.findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(context, MyActivity.class);//启动MainActivity
                startActivity(it);
            }
        });
        return view;
    }
}
