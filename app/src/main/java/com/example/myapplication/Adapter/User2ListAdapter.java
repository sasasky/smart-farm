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

public class User2ListAdapter extends RecyclerView.Adapter<User2ListAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<order> listOrder;

    private User2ListAdapter.OnItemClickListener mOnItemClickListener;

    public User2ListAdapter(List<order> listOrder) {
        this.listOrder = listOrder;
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
        order Order = listOrder.get(i);
        myViewHolder.Time.setText(Order.getTime());
        if(Order.getOrderState()== order.State.unpaid){
            myViewHolder.State.setText("待支付");
        }else if(Order.getOrderState()== order.State.paid){
            myViewHolder.State.setText("已支付");
        }else if(Order.getOrderState()== order.State.overdue){
            myViewHolder.State.setText("逾期未支付");
        }else if(Order.getOrderState()== order.State.cancelled)
            myViewHolder.State.setText("已取消");
        myViewHolder.Sum.setText(Order.getTotal()+"");
        myViewHolder.name.setText(Order.getName());
        myViewHolder.phone.setText(Order.getPhone());
        myViewHolder.address.setText(Order.getAddress());
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
        return listOrder.size();
    }

    //定义视图管理器
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Pic;
        TextView title;
        TextView Time;
        TextView State;
        TextView Sum;
        TextView name;
        TextView phone;
        TextView address;
        Button add;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Pic = itemView.findViewById(R.id.pic);
            title = itemView.findViewById(R.id.title);
            Time = itemView.findViewById(R.id.time);
            State = itemView.findViewById(R.id.state);
            Sum = itemView.findViewById(R.id.sum);
            add = itemView.findViewById(R.id.add);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
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
