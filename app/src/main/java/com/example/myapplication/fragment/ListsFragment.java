package com.example.myapplication.fragment;

import android.content.Context;
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

public class ListsFragment extends Fragment {
    private String[] titles = new String[]{"全部","待发货"};
    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2_lists, container, false);
        Context context= getActivity();
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ViewPager viewPager = view.findViewById(R.id.viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        tabLayout.addTab(tabLayout.newTab().setText("待发货"));
        AllListFragment all  = new AllListFragment();
        WaitListFragment wait = new WaitListFragment();
        fragmentList.add(all);
        fragmentList.add(wait);
        TabFragmentAdapter tabAdapter = new TabFragmentAdapter(fragmentList, titles, getChildFragmentManager());
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}