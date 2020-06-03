package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.accusation;
import com.example.myapplication.entity.address;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {
    private LayoutInflater mLayoutInflater;
    private List<accusation> mInfos;
    private ReportAdapter.OnItemClickListener listener;

    public ReportAdapter(List<accusation> mInfos) {
        this.mInfos = mInfos;
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

    public void setOnItemClickListener(ReportAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReportAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //绑定子视图
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_item, viewGroup, false);
        ReportAdapter.MyViewHolder myViewHolder = new ReportAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ReportAdapter.MyViewHolder myViewHolder, final int i) {
        accusation Accusation = mInfos.get(i);
        if(Accusation.getType()== accusation.InfoType.product){
            myViewHolder.type.setText("农产品");
        }else if(Accusation.getType()== accusation.InfoType.land){
            myViewHolder.type.setText("土地");
        }
        if(Accusation.getAccuseState()== accusation.State.consent){
            myViewHolder.state.setText("同意举报");
        }else if(Accusation.getAccuseState()== accusation.State.unhandled){
            myViewHolder.state.setText("未处理");
        }else if(Accusation.getAccuseState()== accusation.State.rejected){
            myViewHolder.state.setText("驳回举报");
        }
        myViewHolder.reason.setText(Accusation.getReason());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
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
        TextView type, state, reason;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.type);
            state = itemView.findViewById(R.id.state);
            reason = itemView.findViewById(R.id.reason);
        }
    }
}
