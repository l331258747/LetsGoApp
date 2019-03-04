package com.njz.letsgoapp.adapter.coupon;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.coupon.CouponModel;
import com.njz.letsgoapp.util.StringUtils;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2019/2/21
 * Function:
 */

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder>{
    Context mContext;
    List<CouponModel> datas;
    int type;

    /**
     * 标记展开的item
     */
    private int opened = -1;

    public CouponAdapter(Context mContext, List<CouponModel> datas,int type) {
        this.mContext = mContext;
        this.datas = datas;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;
        final CouponModel data = datas.get(position);
        if (data == null) return;

        holder.tv_price.setText("￥" + data.getTypeMoneyStr());
        holder.tv_title.setText(data.getTitle());
        holder.tv_limit.setText("满" + data.getFillMoney()+"元可用");
        StringUtils.setHtml(holder.tv_rule_content,data.getInstructions());

        if(data.getExpireStatus() == 1 && type == 0){
            String content1 = "<font color='red'>(快过期)</font>有效期至";
            StringUtils.setHtml(holder.tv_expire,content1 + data.getUseEndDateStr());
        }else{
            holder.tv_expire.setText("有效期至" + data.getUseEndDateStr());
        }


        if(type == 0){
            holder.ll_price.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_coupon_select_bg));
            holder.tv_use.setVisibility(View.VISIBLE);
            holder.iv_label.setVisibility(View.GONE);
        }else{
            holder.ll_price.setBackground(ContextCompat.getDrawable(mContext,R.drawable.ic_coupon_select_un_bg));
            holder.tv_use.setVisibility(View.INVISIBLE);
            holder.iv_label.setVisibility(View.VISIBLE);

            if(type == 1){
                holder.iv_label.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.ic_coupon_used));
            }else if(type == -1){
                holder.iv_label.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.ic_coupon_expire));
            }
        }

        holder.tv_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnUserClickListener != null){
                    mOnUserClickListener.onItemClick(position);
                }
            }
        });

        holder.bindView(position);
    }

    public void setData(List<CouponModel> datas) {
        this.datas = datas;
        opened = -1;
        notifyDataSetChanged();
    }

    public void addData(List<CouponModel> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_price,tv_title,tv_limit,tv_expire,tv_rule,tv_use,tv_rule_content;
        LinearLayout ll_price,ll_rule_content;
        ImageView iv_label;
        View view_line;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_price = itemView.findViewById(R.id.tv_price);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_limit = itemView.findViewById(R.id.tv_limit);
            tv_expire = itemView.findViewById(R.id.tv_expire);
            tv_rule = itemView.findViewById(R.id.tv_rule);
            tv_use = itemView.findViewById(R.id.tv_use);
            ll_price = itemView.findViewById(R.id.ll_price);
            iv_label = itemView.findViewById(R.id.iv_label);
            tv_rule_content = itemView.findViewById(R.id.tv_rule_content);
            ll_rule_content = itemView.findViewById(R.id.ll_rule_content);
            view_line = itemView.findViewById(R.id.view_line);
            tv_rule.setOnClickListener(this);
        }

        void bindView(int pos) {
            if (pos == opened){
                ll_rule_content.setVisibility(View.VISIBLE);
                view_line.setVisibility(View.VISIBLE);
            } else{
                ll_rule_content.setVisibility(View.GONE);
                view_line.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener == null) return;

            if (opened == getAdapterPosition()) {
                //当点击的item已经被展开了, 就关闭.
                opened = -1;
//                notifyItemChanged(getAdapterPosition());
                mOnItemClickListener.onReplyClick(getAdapterPosition());
            } else {
                int oldOpened = opened;
                opened = getAdapterPosition();
//                notifyItemChanged(oldOpened);
//                notifyItemChanged(opened);
                mOnItemClickListener.onReplyClick(oldOpened,opened);
            }
        }
    }

    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onReplyClick(int... position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    OnUserClickListener mOnUserClickListener;
    public interface OnUserClickListener {
        void onItemClick(int position);
    }
    public void setOnUserClickListener(OnUserClickListener mOnUserClickListener) {
        this.mOnUserClickListener = mOnUserClickListener;
    }
}
