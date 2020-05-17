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

public class User2ListAdapter extends RecyclerView.Adapter<User2ListAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<list> listList;

    private User2ListAdapter.OnItemClickListener mOnItemClickListener;

    public User2ListAdapter(List<list> listList) {
        this.listList = listList;
    }
    @NonNull
    @Override
    public User2ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user2_list_item, viewGroup, false);
        User2ListAdapter.MyViewHolder myViewHolder=new User2ListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final User2ListAdapter.MyViewHolder myViewHolder, final int i) {
        list list = listList.get(i);
        myViewHolder.Pic.setImageResource(list.getDrawable());
        myViewHolder.Name.setText(list.getName());
        myViewHolder.Num.setText(list.getNum());
        myViewHolder.price.setText(list.getPrice());
        myViewHolder.Time.setText(list.getTime());
        myViewHolder.State.setText(list.getState());
        myViewHolder.Sum.setText(list.getSum());
        myViewHolder.add.setOnClickListener(new View.OnClickListener() {
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
        Button add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Pic = itemView.findViewById(R.id.pic);
            Name = itemView.findViewById(R.id.title);
            Num = itemView.findViewById(R.id.num);
            price = itemView.findViewById(R.id.price);
            Time = itemView.findViewById(R.id.time);
            State = itemView.findViewById(R.id.state);
            Sum = itemView.findViewById(R.id.sum);
            add = itemView.findViewById(R.id.add);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private User2ListAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(User2ListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
