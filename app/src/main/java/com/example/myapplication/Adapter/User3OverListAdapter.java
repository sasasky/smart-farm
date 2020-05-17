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
import com.example.myapplication.entity.list;

import java.util.List;

public class User3OverListAdapter extends RecyclerView.Adapter<User3OverListAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<list> listList;

    private User3OverListAdapter.OnItemClickListener mOnItemClickListener;

    public User3OverListAdapter(List<list> listList) {
        this.listList = listList;
    }
    @NonNull
    @Override
    public User3OverListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user3_over_list_item, viewGroup, false);
        User3OverListAdapter.MyViewHolder myViewHolder=new User3OverListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final User3OverListAdapter.MyViewHolder myViewHolder, final int i) {
        list list = listList.get(i);
        myViewHolder.Pic.setImageResource(list.getDrawable());
        myViewHolder.Name.setText(list.getName());
        myViewHolder.Num.setText(list.getNum());
        myViewHolder.price.setText(list.getPrice());
        myViewHolder.Time.setText(list.getTime());
        myViewHolder.State.setText(list.getState());
        myViewHolder.Sum.setText(list.getSum());
        myViewHolder.logistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemInnerDeleteListener.onItemInnerDeleteClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listList.size();
    }

    //定义视图管理器
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Pic;
        TextView Name;
        TextView Num;
        TextView price;
        TextView Time;
        TextView State;
        TextView Sum;
        Button logistics;
        Button confirm;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Pic = itemView.findViewById(R.id.pic);
            Name = itemView.findViewById(R.id.title);
            Num = itemView.findViewById(R.id.num);
            price = itemView.findViewById(R.id.price);
            Time = itemView.findViewById(R.id.time);
            State = itemView.findViewById(R.id.state);
            Sum = itemView.findViewById(R.id.sum);
            logistics = itemView.findViewById(R.id.logistics);
            confirm = itemView.findViewById(R.id.confirm);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public interface ItemInnerDeleteListener {
        void onItemInnerDeleteClick(int position);
    }
    private User3OverListAdapter.ItemInnerDeleteListener mItemInnerDeleteListener;

    public void setOnItemDeleteClickListener(ItemInnerDeleteListener mItemInnerDeleteListener) {
        this.mItemInnerDeleteListener = mItemInnerDeleteListener;
    }

    private User3OverListAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(User3OverListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}