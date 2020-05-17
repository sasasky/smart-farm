package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.ShopInfo;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<ShopInfo> mInfos;
    private Context mContext;
    private View.OnClickListener mOnClickListener;
    private OnItemClickListener listener;
    private CompoundButton.OnCheckedChangeListener mChangeListener;

    public CartAdapter(List<ShopInfo> infos, Context context) {
        mContext = context;
        mInfos = infos;
        mChangeListener = (CompoundButton.OnCheckedChangeListener) mContext;
        mOnClickListener = (View.OnClickListener) mContext;
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

    public void setOnItemClickListener(CartAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, viewGroup, false);
        CartAdapter.MyViewHolder myViewHolder = new CartAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.MyViewHolder myViewHolder, final int i) {
        ShopInfo shopInfo = mInfos.get(i);
        myViewHolder.mTextViewInfo.setText(mInfos.get(i).mName);
        myViewHolder.mTextViewPrice.setText(mInfos.get(i).mPrice+ "");
        myViewHolder.mTextViewCount.setText(mInfos.get(i).mNumber + "");
        myViewHolder.mImageViewIcon.setImageResource(mInfos.get(i).mDrawable);
        //设置CheckBox的选中状态
        myViewHolder.mCheckBox.setChecked(mInfos.get(i).isCheck);
        myViewHolder.mCheckBox.setOnCheckedChangeListener(mChangeListener);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, i);
                }

            }
        });
        myViewHolder.mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, i);
                }
            }
        });
        myViewHolder.mButtonSub.setOnClickListener(new View.OnClickListener() {
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
        private TextView mTextViewInfo, mTextViewCount, mTextViewPrice;
        private ImageView mImageViewIcon;
        private CheckBox mCheckBox;
        private Button mButtonAdd, mButtonSub;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewInfo = (TextView) itemView.findViewById(R.id.tv_item_info);
            mTextViewCount = (TextView) itemView.findViewById(R.id.tv_item_count);
            mTextViewPrice = (TextView) itemView.findViewById(R.id.tv_item_price);
            mImageViewIcon = (ImageView) itemView.findViewById(R.id.pic);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_item_check);
            mButtonAdd = (Button) itemView.findViewById(R.id.bt_item_add);
            mButtonSub = (Button) itemView.findViewById(R.id.bt_item_subtract);
        }
    }

    public int getMoney() {
        int money = 0;
        if (mInfos == null || mInfos.size() == 0) {
            return money;
        }
        for (int i = 0; i < mInfos.size(); i++) {
            ShopInfo info = mInfos.get(i);
            if (info.isCheck) {
                int itemMoney = info.mNumber * info.mPrice;
                money += itemMoney;
            }
        }
        return money;
    }
}
