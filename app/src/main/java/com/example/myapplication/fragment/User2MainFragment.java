package com.example.myapplication.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.TabFragmentAdapter;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class User2MainFragment  extends Fragment {
    private String[] titles = new String[]{"蔬菜","水果","其他"};
    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2_goods, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        final Context context = getActivity();
        tabLayout.addTab(tabLayout.newTab().setText("蔬菜"));
        tabLayout.addTab(tabLayout.newTab().setText("水果"));
        tabLayout.addTab(tabLayout.newTab().setText("其他"));
        User2VegeFragment vegetable  = new User2VegeFragment();
        User2FruitFragment fruit = new User2FruitFragment();
        User2OtherFragment other = new User2OtherFragment();
        fragmentList.add(vegetable);
        fragmentList.add(fruit);
        fragmentList.add(other);
        TabFragmentAdapter tabAdapter = new TabFragmentAdapter(fragmentList, titles, getChildFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        Typeface font = Typeface.createFromAsset(requireActivity().getAssets(), "iconfont.ttf");
//        TextView search =view.findViewById(R.id.button_search);
//        search.setTypeface(font);
        return view;
    }
}