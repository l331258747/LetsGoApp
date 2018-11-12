package com.njz.letsgoapp.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.adapter.order.SimpleImageAdapter;
import com.njz.letsgoapp.bean.home.EvaluateModel;
import com.njz.letsgoapp.bean.home.EvaluateModel2;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.other.BigImageActivity;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/17
 * Function:
 */

public class EvaluateAdapter extends RecyclerView.Adapter<EvaluateAdapter.ViewHolder> {
    Context mContext;
    List<EvaluateModel2> datas;

    /**
     * 标记展开的item
     */
    private int opened = -1;

    public EvaluateAdapter(Context mContext, List<EvaluateModel2> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) return;
        final int pos = holder.getAdapterPosition();
        final EvaluateModel2 data = datas.get(pos);
        if (data == null) return;

        GlideUtil.LoadCircleImage(mContext, data.getImgUrl(), holder.commont_head);

        holder.commont_name.setText(data.getNickname());
        holder.commont_time.setText(data.getUserDate());
        holder.commont_score.setText(data.getScore());
        holder.tv_comment_content.setText(data.getUserContent());

        if(TextUtils.isEmpty(data.getGuideContent())){
            holder.rl_reply.setVisibility(View.GONE);
        }else{
            holder.rl_reply.setVisibility(View.VISIBLE);
            holder.reply_time.setText(data.getGuideDate());
            holder.reply_content.setText(data.getGuideContent());
        }

        if(TextUtils.isEmpty(data.getServicesStr())){
            holder.ll_click.setVisibility(View.GONE);
        }else{
            holder.ll_click.setVisibility(View.VISIBLE);
            holder.tv_order.setText(data.getServicesStr());
        }

        holder.tv_comment_guide.setVisibility(View.GONE);
        holder.tv_comment_trip.setVisibility(View.GONE);
        holder.tv_comment_car.setVisibility(View.GONE);
        holder.tv_comment_book.setVisibility(View.GONE);
        if(data.getGuideService() > 0 ){
            holder.tv_comment_guide.setVisibility(View.VISIBLE);
            holder.tv_comment_guide.setText(data.getGuideServiceStr());
        }
        if(data.getTravelArrange() > 0 ){
            holder.tv_comment_trip.setVisibility(View.VISIBLE);
            holder.tv_comment_trip.setText(data.getTravelArrangeStr());
        }
        if(data.getCarCondition() > 0){
            holder.tv_comment_car.setVisibility(View.VISIBLE);
            holder.tv_comment_car.setText(data.getCarConditionStr());
        }
        if(data.getBuyService() > 0){
            holder.tv_comment_book.setVisibility(View.VISIBLE);
            holder.tv_comment_book.setText(data.getBuyServiceStr());
        }

        holder.mRecyclerView.setNestedScrollingEnabled(false);//滑动取消
        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(
                holder.mRecyclerView.getContext(), 4));

        if(data.getImageUrls() == null || data.getImageUrls().size() == 0){
            holder.ll_photo.setVisibility(View.GONE);
        }else{
            holder.ll_photo.setVisibility(View.VISIBLE);
            SimpleImageAdapter enterAdapter = new SimpleImageAdapter(mContext, data.getImageUrls());
            enterAdapter.setOnItemClickListener(new SimpleImageAdapter.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    BigImageActivity.startActivity((Activity) mContext,position,data.getImageUrls());
                }
            });
            holder.mRecyclerView.setAdapter(enterAdapter);
        }



        holder.bindView(pos);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<EvaluateModel2> datas) {
        this.datas = datas;
        opened = -1;
        notifyDataSetChanged();
    }

    public List<EvaluateModel2> getDatas(){
        return this.datas;
    }

    public void addData(List<EvaluateModel2> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView commont_head,iv_open_service;
        TextView commont_name, commont_time, commont_score, tv_comment_content, tv_order, reply_time, reply_content;
        RelativeLayout rl_reply;
        RecyclerView mRecyclerView;
        TextView tv_comment_guide,tv_comment_trip,tv_comment_car,tv_comment_book;
        LinearLayout ll_order,ll_click,ll_photo;

        TextView tv_click;

        ViewHolder(View itemView) {
            super(itemView);
            commont_head = itemView.findViewById(R.id.comment_head);
            ll_order = itemView.findViewById(R.id.ll_order);
            ll_photo = itemView.findViewById(R.id.ll_photo);
            commont_name = itemView.findViewById(R.id.commont_name);
            commont_time = itemView.findViewById(R.id.commont_time);
            commont_score = itemView.findViewById(R.id.commont_score);
            tv_comment_content = itemView.findViewById(R.id.tv_comment_content);
            tv_order = itemView.findViewById(R.id.tv_order);
            reply_time = itemView.findViewById(R.id.reply_time);
            reply_content = itemView.findViewById(R.id.reply_content);
            rl_reply = itemView.findViewById(R.id.rl_reply);
            mRecyclerView = itemView.findViewById(R.id.recycler_view);
            tv_comment_guide = itemView.findViewById(R.id.tv_comment_guide);
            tv_comment_trip = itemView.findViewById(R.id.tv_comment_trip);
            tv_comment_car = itemView.findViewById(R.id.tv_comment_car);
            tv_comment_book = itemView.findViewById(R.id.tv_comment_book);
            iv_open_service = itemView.findViewById(R.id.iv_open_service);
            tv_click = itemView.findViewById(R.id.tv_click);
            ll_click = itemView.findViewById(R.id.ll_click);
            ll_click.setOnClickListener(this);


        }

        void bindView(int pos) {
            if (pos == opened){
                ll_order.setVisibility(View.VISIBLE);
                iv_open_service.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.evaluate_open));
            } else{
                ll_order.setVisibility(View.GONE);
                iv_open_service.setImageDrawable(ContextCompat.getDrawable(mContext,R.mipmap.evaluate_open_un));
            }
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener == null) return;

            if (opened == getAdapterPosition()) {
                //当点击的item已经被展开了, 就关闭.
                opened = -1;
                notifyItemChanged(getAdapterPosition());
                mOnItemClickListener.onReplyClick(getAdapterPosition());
            } else {
                int oldOpened = opened;
                opened = getAdapterPosition();
                notifyItemChanged(oldOpened);
                notifyItemChanged(opened);
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
}
