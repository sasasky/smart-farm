package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.land;

import java.util.List;

public class AdminLandAdapter extends RecyclerView.Adapter<AdminLandAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<land> landList;

    private User2LandAdapter.OnItemClickListener mOnItemClickListener;

    public AdminLandAdapter(List<land> landList) {
        this.landList = landList;
    }
    @NonNull
    @Override
    public AdminLandAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.admin_land_item, viewGroup, false);
        AdminLandAdapter.MyViewHolder myViewHolder=new AdminLandAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdminLandAdapter.MyViewHolder myViewHolder, final int i) {
        land land = landList.get(i);
        myViewHolder.Land_Pic.setImageBitmap(land.getphotoUrl());
        myViewHolder.Land_Name.setText(land.getlocation());
        myViewHolder.Land_area.setText(Double.toString(land.getarea()));
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
        TextView Land_area;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Land_Pic = itemView.findViewById(R.id.Land_Pic);
            Land_Name = itemView.findViewById(R.id.Land_Name);
            Land_area = itemView.findViewById(R.id.Land_area);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private AdminLandAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(AdminLandAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
