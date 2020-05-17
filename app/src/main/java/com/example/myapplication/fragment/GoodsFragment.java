package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.Adapter.TabFragmentAdapter;
import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class GoodsFragment extends Fragment {
    private String[] titles = new String[]{"蔬菜","水果","其他"};
    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2_goods, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("蔬菜"));
        tabLayout.addTab(tabLayout.newTab().setText("水果"));
        tabLayout.addTab(tabLayout.newTab().setText("其他"));
        User1VegeFragment vegetable  = new User1VegeFragment();
        User1FruitFragment fruit = new User1FruitFragment();
        User1OtherFragment other = new User1OtherFragment();
        fragmentList.add(vegetable);
        fragmentList.add(fruit);
        fragmentList.add(other);
        TabFragmentAdapter tabAdapter = new TabFragmentAdapter(fragmentList, titles, getChildFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}