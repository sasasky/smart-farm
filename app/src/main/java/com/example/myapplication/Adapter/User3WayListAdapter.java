package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.order;

import java.util.List;

public class User3WayListAdapter extends RecyclerView.Adapter<User3WayListAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<order> listOrder;

    private User3WayListAdapter.OnItemClickListener mOnItemClickListener;

    public User3WayListAdapter(List<order> listOrder) {
        this.listOrder = listOrder;
    }
    @NonNull
    @Override
    public User3WayListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user3_way_list_item, viewGroup, false);
        User3WayListAdapter.MyViewHolder myViewHolder=new User3WayListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final User3WayListAdapter.MyViewHolder myViewHolder, final int i) {
        order order = listOrder.get(i);
        myViewHolder.Time.setText(order.getTime());
        myViewHolder.State.setText((int) order.getTotal());
        myViewHolder.Sum.setText(order.getTotal()+"");
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    //定义视图管理器
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Pic;
        TextView Name;
        TextView Time;
        TextView State;
        TextView Sum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Pic = itemView.findViewById(R.id.pic);
            Name = itemView.findViewById(R.id.title);
            Time = itemView.findViewById(R.id.time);
            State = itemView.findViewById(R.id.state);
            Sum = itemView.findViewById(R.id.sum);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private User3WayListAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(User3WayListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}


