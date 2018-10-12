package com.njz.letsgoapp.adapter.notify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.mine.MyCommentModel;
import com.njz.letsgoapp.bean.notify.NotifyMainModel;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/23
 * Function:
 */

public class SystemMsgAdapter extends RecyclerView.Adapter<SystemMsgAdapter.ViewHolder> {

    Context mContext;
    List<NotifyMainModel> datas;

    public SystemMsgAdapter(Context mContext, List<NotifyMainModel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_system_msg, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder == null) return;
        final NotifyMainModel data = datas.get(position);
        if (data == null) return;

        holder.tv_name.setText(data.getTitle());
        holder.tv_time.setText(data.getCreateDate());
        holder.tv_content.setText(data.getContent().getAlert());

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

        TextView tv_name,tv_time,tv_content;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }

}
