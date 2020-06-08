package com.example.myapplication.Adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.land;

public class User2LandAdapter extends RecyclerView.Adapter<User2LandAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<land> landList;

    private User2LandAdapter.OnItemClickListener mOnItemClickListener;

    public User2LandAdapter(List<land> landList) {
        this.landList = landList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user2_land_item, viewGroup, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        land land = landList.get(i);
        myViewHolder.Land_Pic.setImageBitmap(land.getphotoUrl());
        myViewHolder.Land_Name.setText(land.getlocation());
        myViewHolder.Land_price.setText(land.getrent()+" ");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(i);
                }
            }
        });
        if(land.getstate()== com.example.myapplication.entity.land.land_State.leased){
            myViewHolder.itemView.setBackgroundResource(R.drawable.unable_card);
        }
    }

    @Override
    public int getItemCount() {
        return landList.size();
    }

    //定义视图管理器
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Land_Pic;
        TextView Land_Name;
        TextView Land_price;
        Button detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Land_Pic = itemView.findViewById(R.id.Land_Pic);
            Land_Name = itemView.findViewById(R.id.Land_Name);
            Land_price = itemView.findViewById(R.id.Land_price);
            detail = itemView.findViewById(R.id.detail);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}