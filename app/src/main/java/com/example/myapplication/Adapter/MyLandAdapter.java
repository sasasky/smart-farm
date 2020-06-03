package com.example.myapplication.Adapter;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.land;
import com.example.myapplication.entity.lease;

public class MyLandAdapter extends RecyclerView.Adapter<MyLandAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<lease> landList;

    private User2LandAdapter.OnItemClickListener mOnItemClickListener;

    public MyLandAdapter(List<lease> landList) {
        this.landList = landList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_land_item, viewGroup, false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        lease land = landList.get(i);
        myViewHolder.Land_Pic.setImageBitmap(land.getphotoUrl());
        myViewHolder.Land_Name.setText(land.getlocation());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return landList.size();
    }

    //定义视图管理器
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Land_Pic;
        TextView Land_Name;
        TextView Land_Detail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Land_Pic = itemView.findViewById(R.id.Land_Pic);
            Land_Name = itemView.findViewById(R.id.Land_Name);
            Land_Detail = itemView.findViewById(R.id.Land_Detail);
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