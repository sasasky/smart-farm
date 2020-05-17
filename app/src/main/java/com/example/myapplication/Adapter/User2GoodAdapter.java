package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.goods;

import java.util.List;

public class User2GoodAdapter extends RecyclerView.Adapter<User2GoodAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<goods> goodsList;

    private User2GoodAdapter.OnItemClickListener mOnItemClickListener;

    public User2GoodAdapter(List<goods> goodsList) {
        this.goodsList = goodsList;
    }
    @NonNull
    @Override
    public User2GoodAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user2_good_item, viewGroup, false);
        User2GoodAdapter.MyViewHolder myViewHolder=new User2GoodAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final User2GoodAdapter.MyViewHolder myViewHolder, final int i) {
        goods good = goodsList.get(i);
        myViewHolder.Pic.setImageResource(good.getDrawable());
        myViewHolder.Name.setText(good.getName());
        myViewHolder.People.setText(good.getPeople());
        myViewHolder.price.setText(good.getPrice());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(i);
                }
            }

            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onItemLongClick(i);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    //定义视图管理器
    static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView Pic;
        TextView Name;
        TextView People;
        TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Pic = itemView.findViewById(R.id.Good_pic);
            Name = itemView.findViewById(R.id.Good_name);
            price = itemView.findViewById(R.id.Good_price);
            People = itemView.findViewById(R.id.Good_people);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
        void onItemLongClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(User2GoodAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
