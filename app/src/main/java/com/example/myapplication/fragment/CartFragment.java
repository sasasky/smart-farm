package com.example.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.R;
import com.example.myapplication.entity.ShopInfo;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private ListView mListView;
    private List<ShopInfo> mData;
    private CheckBox mCheckBox;
    private Button mButtonMode;
    private TextView mTextViewMoney;
    private CartAdapter mAdapter;
    private Context context=getActivity();
    private int money;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user3_cart,container,false);
        RecyclerView rv = view.findViewById(R.id.recyclerView);
        mCheckBox= (CheckBox) view.findViewById(R.id.checkAll);
        mButtonMode= (Button) view.findViewById(R.id.pay);
        mTextViewMoney= (TextView) view.findViewById(R.id.money);
        initData();
        mAdapter=new CartAdapter(mData,context);
        mAdapter.setOnItemClickListener(MyItemClickListener);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch(buttonView.getId()){
                    case R.id.checkAll:
                        for (int i = 0; i <mData.size(); i++) {
                            mData.get(i).isCheck=isChecked;
                        }
                        money=mAdapter.getMoney();
                        mTextViewMoney.setText(money);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case R.id.cb_item_check:
                        //获取与item中CheckBox关联的位置
                        Integer position= (Integer) buttonView.getTag();
                        if(position!=null){
                            mData.get(position).isCheck=isChecked;
                            money=mAdapter.getMoney();
                            mTextViewMoney.setText(money);
                            mAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setHasFixedSize(true);
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(linearLayoutManager);
        return view;
    }

    private void initData() {
        //初始化数据 自己模拟的数据
        mData=new ArrayList<>();
        mData.add(new ShopInfo("新鲜玉米 甜玉米 爆浆玉米 8斤装",80,2,R.drawable.carrot,false));
        mData.add(new ShopInfo("胡罗卜 2斤装 健康新鲜",50,3,R.drawable.carrot,false));
        mData.add(new ShopInfo("新鲜玉米 甜玉米 爆浆玉米 8斤装",80,4,R.drawable.carrot,false));
        mData.add(new ShopInfo("胡罗卜 2斤装 健康新鲜",50,1,R.drawable.carrot,false));
    }

    private CartAdapter.OnItemClickListener MyItemClickListener = new CartAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()){
                case R.id.bt_item_add:
                    mData.get(position).mNumber++;
                    mAdapter.notifyDataSetChanged();
                    break;
                case R.id.bt_item_subtract:
                    mData.get(position).mNumber--;
                    if(mData.get(position).mNumber<=0){
                        mData.remove((int)position);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
