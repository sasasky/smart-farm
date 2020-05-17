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

public class User3PayListAdapter extends RecyclerView.Adapter<User3PayListAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<list> listList;

    private User3PayListAdapter.OnItemClickListener mOnItemClickListener;

    public User3PayListAdapter(List<list> listList) {
        this.listList = listList;
    }
    @NonNull
    @Override
    public User3PayListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user3_pay_list_item, viewGroup, false);
        User3PayListAdapter.MyViewHolder myViewHolder=new User3PayListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final User3PayListAdapter.MyViewHolder myViewHolder, final int i) {
        list list = listList.get(i);
        myViewHolder.Pic.setImageResource(list.getDrawable());
        myViewHolder.Name.setText(list.getName());
        myViewHolder.Num.setText(list.getNum());
        myViewHolder.price.setText(list.getPrice());
        myViewHolder.Time.setText(list.getTime());
        myViewHolder.State.setText(list.getState());
        myViewHolder.Sum.setText(list.getSum());
        myViewHolder.pay.setOnClickListener(new View.OnClickListener() {
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
        Button pay;
        Button delete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Pic = itemView.findViewById(R.id.pic);
            Name = itemView.findViewById(R.id.title);
            Num = itemView.findViewById(R.id.num);
            price = itemView.findViewById(R.id.price);
            Time = itemView.findViewById(R.id.time);
            State = itemView.findViewById(R.id.state);
            Sum = itemView.findViewById(R.id.sum);
            pay = itemView.findViewById(R.id.pay);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    private User3PayListAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(User3PayListAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}

