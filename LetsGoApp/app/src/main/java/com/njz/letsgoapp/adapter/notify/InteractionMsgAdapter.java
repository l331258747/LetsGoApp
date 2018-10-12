package com.njz.letsgoapp.adapter.notify;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.mine.MyCommentAdapter;
import com.njz.letsgoapp.bean.mine.MyCommentModel;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.find.DynamicDetailActivity;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/23
 * Function:
 */

public class InteractionMsgAdapter extends RecyclerView.Adapter<InteractionMsgAdapter.ViewHolder> {

    Context mContext;
    List<NotifyMainModel> datas;

    public InteractionMsgAdapter(Context mContext, List<NotifyMainModel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_interaction_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder == null) return;
        final NotifyMainModel data = datas.get(position);
        if (data == null) return;

        GlideUtil.LoadCircleImage(mContext, data.getContent().getOther().getImg(), holder.iv_head);
        holder.tv_name.setText(data.getContent().getOther().getName());
        holder.tv_time.setText(data.getCreateDate());

        holder.tv_mine2.setText(data.getContent().getOther().getType());
    }

    public void setData(List<NotifyMainModel> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addData(List<NotifyMainModel> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_head;
        TextView tv_name,tv_time,tv_mine2;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_mine2 = itemView.findViewById(R.id.tv_mine2);
        }
    }
}
