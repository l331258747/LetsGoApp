package com.njz.letsgoapp.adapter.mine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.home.GuideListAdapter;
import com.njz.letsgoapp.bean.mine.FansBean;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/17
 * Function:
 */

public class FansListAdapter extends RecyclerView.Adapter<FansListAdapter.ViewHolder> {

    Context mContext;
    List<FansBean> fans;

    public FansListAdapter(Context mContext, List<FansBean> fans) {
        this.mContext = mContext;
        this.fans = fans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_fans, parent, false);
        return new FansListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) return;
            final int pos = holder.getAdapterPosition();
            final FansBean data = fans.get(pos);
            if (data == null) return;

            GlideUtil.LoadCircleImage(mContext, data.getHeadImg(), holder.iv_head);
            holder.tv_name.setText(data.getName());

            if(pos == getItemCount() - 1){
                holder.view_line.setVisibility(View.GONE);
            }else{
                holder.view_line.setVisibility(View.VISIBLE);
            }

            if (mOnItemClickListener != null) {
                holder.rl_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(pos);
                    }
                });
            }

    }

    @Override
    public int getItemCount() {
        return fans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_head;
        TextView tv_name;
        View view_line;
        RelativeLayout rl_parent;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            view_line = itemView.findViewById(R.id.view_line);
            rl_parent = itemView.findViewById(R.id.rl_parent);
        }
    }

    GuideListAdapter.OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(GuideListAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<FansBean> fans){
        this.fans = fans;
        notifyDataSetChanged();
    }
}
