package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<address> mInfos;
    private Context mContext;
    private AddressAdapter.OnItemClickListener listener;

    public AddressAdapter(List<address> infos, Context context) {
        mContext = context;
        mInfos = infos;
    }

    @Override
    public int getItemCount() {
        return mInfos == null ? 0 : mInfos.size();
    }

    public Object getItem(int i) {
        return mInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(AddressAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AddressAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, viewGroup, false);
        AddressAdapter.MyViewHolder myViewHolder = new AddressAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressAdapter.MyViewHolder myViewHolder, final int i) {
        address Address = mInfos.get(i);
        myViewHolder.name.setText(Address.getName());
        myViewHolder.phone.setText(Address.getPhone());
        myViewHolder.address.setText(Address.getAddress());
        myViewHolder.context.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, i);
                }

            }
        });
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, i);
                }
            }
        });
    }

    //定义视图管理器
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, address;
        ImageButton delete;
        LinearLayout context;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            address = itemView.findViewById(R.id.address);
            context = itemView.findViewById(R.id.context);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}

