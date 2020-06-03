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

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<goods> goodsList;

    private ProductAdapter.OnItemClickListener mOnItemClickListener;

    public ProductAdapter(List<goods> goodsList) {
        this.goodsList = goodsList;
    }
    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, viewGroup, false);
        ProductAdapter.MyViewHolder myViewHolder=new ProductAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.MyViewHolder myViewHolder, final int i) {
        goods good = goodsList.get(i);
        myViewHolder.Pic.setImageBitmap(good.getphotoUrl());
        myViewHolder.Name.setText(good.gettype());
        myViewHolder.price.setText(good.getprice()+"");
        myViewHolder.num.setText(good.getquantity()+"");
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
        TextView price;
        TextView num;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Pic = itemView.findViewById(R.id.pic);
            Name = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            num = itemView.findViewById(R.id.num);
        }
    }

    public interface OnItemClickListener {
        void onClick(int position);
        void onItemLongClick(int position);
    }

    private ProductAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ProductAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
