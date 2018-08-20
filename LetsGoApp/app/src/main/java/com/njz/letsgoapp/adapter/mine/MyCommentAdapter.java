package com.njz.letsgoapp.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.mine.MyCommentBean;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/20
 * Function:
 */

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolder> {

    Context mContext;
    List<MyCommentBean> datas;

    public MyCommentAdapter(Context mContext, List<MyCommentBean> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_my_comment, parent, false);
        return new MyCommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) return;
        final MyCommentBean data = datas.get(position);
        if (data == null) return;

        GlideUtil.LoadCircleImage(mContext, data.getHeadUrl(), holder.iv_head);
        GlideUtil.LoadImage(mContext, data.getBodyUrl(), holder.iv_body_img);
        holder.tv_name.setText(data.getName());
        holder.tv_content.setText(data.getContent());
        holder.tv_time.setText(data.getTime());
        holder.tv_body_content.setText(data.getBodyContent());

    }

    public void setData(List<MyCommentBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_head, iv_body_img;
        TextView tv_name, tv_content, tv_time, tv_body_content;
        View view_line;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_body_content = itemView.findViewById(R.id.tv_body_content);
            iv_body_img = itemView.findViewById(R.id.iv_body_img);
            view_line = itemView.findViewById(R.id.view_line);
        }
    }
}
