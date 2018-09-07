package com.njz.letsgoapp.adapter.find;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.MySelfInfo;
import com.njz.letsgoapp.bean.find.DynamicCommentModel;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/7
 * Function:
 */

public class DynamicCommentAdapter extends RecyclerView.Adapter<DynamicCommentAdapter.ViewHodler> {


    Context mContext;
    List<DynamicCommentModel> datas;

    public DynamicCommentAdapter(Context mContext, List<DynamicCommentModel> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_dynamic_comment, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, final int position) {
        if (holder == null) return;
        final int pos = holder.getAdapterPosition();
        final DynamicCommentModel data = datas.get(pos);
        if (data == null) return;

        GlideUtil.LoadCircleImage(mContext, data.getDiscussUserUrl(), holder.iv_head);
        holder.tv_name.setText(data.getDiscussUserName());
        holder.tv_time.setText(data.getDiscussTime());
        if(data.getToUserId() > 0){
            holder.tv_toName.setVisibility(View.VISIBLE);
            holder.tv_toName.setText("回复 "+ data.getToUserName() + " : ");
        }else{
            holder.tv_toName.setVisibility(View.GONE);
        }
        holder.tv_content.setText(data.getDiscussContent());

        if(mOnItemClickListener != null){
            holder.rl_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder{
        ImageView iv_head;
        TextView tv_name,tv_time,tv_toName,tv_content;
        RelativeLayout rl_parent;

        public ViewHodler(View itemView) {
            super(itemView);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_toName = itemView.findViewById(R.id.tv_toName);
            tv_content = itemView.findViewById(R.id.tv_content);
            rl_parent = itemView.findViewById(R.id.rl_parent);
        }
    }

    public void setData(List<DynamicCommentModel> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
