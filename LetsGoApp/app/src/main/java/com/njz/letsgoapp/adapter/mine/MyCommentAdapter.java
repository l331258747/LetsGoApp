package com.njz.letsgoapp.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.mine.MyCommentModel;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/20
 * Function:
 */

public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolder> {

    Context mContext;
    List<MyCommentModel> datas;
    int type = 1;

    public MyCommentAdapter(Context mContext, List<MyCommentModel> datas,int type) {
        this.mContext = mContext;
        this.datas = datas;
        this.type = type;
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
        final MyCommentModel data = datas.get(position);
        if (data == null) return;

        GlideUtil.LoadCircleImage(mContext, data.getImgUrl(), holder.iv_head);
        holder.tv_name.setText(data.getNickname());
        if(type == 1){
            holder.tv_mine.setVisibility(View.GONE);
            holder.tv_mine2.setVisibility(View.VISIBLE);
        }else{
            holder.tv_mine.setVisibility(View.VISIBLE);
            holder.tv_mine2.setVisibility(View.GONE);
        }
        holder.tv_time.setText(data.getDiscussTime());
        holder.tv_content.setText(data.getDiscussContent());
    }

    public void setData(List<MyCommentModel> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_head;
        TextView tv_mine, tv_name, tv_mine2, tv_time,tv_content;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_head = itemView.findViewById(R.id.iv_head);
            tv_mine = itemView.findViewById(R.id.tv_mine);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_mine2 = itemView.findViewById(R.id.tv_mine2);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
