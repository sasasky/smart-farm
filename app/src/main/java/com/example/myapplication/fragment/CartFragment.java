package com.example.myapplication.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.CartAdapter;
import com.example.myapplication.GoodsDetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.SelectAddressActivity;
import com.example.myapplication.entity.ShopInfo;
import com.example.myapplication.entity.ShopInfoList;
import com.example.myapplication.entity.goodslist;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.landlist;
import com.example.myapplication.service.CartService;
import com.example.myapplication.service.GoodService;
import com.example.myapplication.service.LandService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {
    private List<ShopInfo> shoplist = new ArrayList();
    private CheckBox mCheckBox;
    private Button mButtonMode;
    private TextView mTextViewMoney;
    private RecyclerView rv;
    private CartAdapter mAdapter;
    private Context context;
    private double money;
    private List<Integer> pIdlist= new ArrayList();
    private String userId;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user3_cart,container,false);
        context=getActivity();
        rv = view.findViewById(R.id.recyclerView);
        mCheckBox= (CheckBox) view.findViewById(R.id.checkAll);
        mButtonMode= (Button) view.findViewById(R.id.pay);
        mTextViewMoney= (TextView) view.findViewById(R.id.money);
        Bundle bundle=getArguments();
        if(bundle!=null){
            userId=bundle.getString("userId");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        CartService request = retrofit.create(CartService.class);
        Call<ShopInfoList> call = request.getItem(userId);
        call.enqueue(new Callback<ShopInfoList>() {
            @Override
            public void onResponse(Call<ShopInfoList> call, Response<ShopInfoList> response) {
                List<ShopInfo> shopinfo= response.body().getData();
                for(ShopInfo info:shopinfo){
                    pIdlist.add(info.getProductId());
                }
                shoplist.addAll(shopinfo);
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ShopInfoList> call, Throwable throwable) {
                System.out.println("连接失败");
                System.out.println(throwable.getMessage());
            }
        });

        mAdapter=new CartAdapter(shoplist,context);
        mAdapter.setOnItemClickListener(MyItemClickListener);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch(buttonView.getId()){
                    case R.id.checkAll:
                        for (int i = 0; i <shoplist.size(); i++) {
                            shoplist.get(i).isCheck=isChecked;
                        }
                        money=mAdapter.getMoney();
                        mTextViewMoney.setText(money+"");
                        System.out.println(money);
                        mAdapter.notifyDataSetChanged();
                        break;
                    case R.id.cb_item_check:
                        //获取与item中CheckBox关联的位置
                        Integer position= (Integer) buttonView.getTag();
                        if(position!=null){
                            shoplist.get(position).isCheck=isChecked;
                            money=mAdapter.getMoney();
                            mTextViewMoney.setText(money+"");
                            System.out.println(money);
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
        mButtonMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(pIdlist);
                Intent it=new Intent(context, SelectAddressActivity.class);//启动MainActivity
                it.putIntegerArrayListExtra("pIdlist", (ArrayList<Integer>) pIdlist);
                it.putExtra("userId",userId);
                startActivity(it);
            }
        });
        return view;
    }

    private CartAdapter.OnItemClickListener MyItemClickListener = new CartAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, final int position) {
            switch (v.getId()){
                case R.id.delete:
                    shoplist.remove(position);
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://47.102.99.47:8888/") // 设置 网络请求 Url
                            .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                            .build();
                    CartService request = retrofit.create(CartService.class);
                    Call<ShopInfo> call = request.deleteItem(userId,shoplist.get(position).getProductId());
                    call.enqueue(new Callback<ShopInfo>() {
                        @Override
                        public void onResponse(Call<ShopInfo> call, Response<ShopInfo> response) {
                            System.out.println("连接成功");
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onFailure(Call<ShopInfo> call, Throwable throwable) {
                            System.out.println("连接失败");
                            System.out.println(throwable.getMessage());
                        }
                    });
                    break;
            }
        }
    };
}
