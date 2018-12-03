package com.njz.letsgoapp.adapter.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.GuideScoreView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class GuideListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context mContext;
    List<GuideModel> guideDatas;

    public GuideListAdapter(Context context, List<GuideModel> guideDatas) {
        this.mContext = context;
        this.guideDatas = guideDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_guide, parent, false);
        return new GuideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof GuideListAdapter.GuideViewHolder) {
            final int pos = holder.getAdapterPosition();
            final GuideModel data = guideDatas.get(pos);
            if (data == null) return;

            GlideUtil.LoadCircleImage(mContext, data.getGuideImg(), ((GuideViewHolder) holder).iv_head);
            GlideUtil.LoadTopRoundImage(mContext, data.getGuideImg(), ((GuideViewHolder) holder).iv_backimg,5);
            ((GuideViewHolder) holder).tv_name.setText(data.getGuideName());
            ((GuideViewHolder) holder).rating_bar_route.setRating((int)data.getGuideScore());
            ((GuideViewHolder) holder).tv_content.setText(data.getIntroduce());
            ((GuideViewHolder) holder).tv_service_item.setServiceTag(data.getServiceTags());
            ((GuideViewHolder) holder).ll_times.setGuideScore(data.getServiceCounts(),data.getGuideScore(),data.getCount());
            if (mOnItemClickListener != null) {
                ((GuideViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(pos);
                    }
                });
            }
            ((GuideViewHolder) holder).iv_sex.setImageDrawable(data.getGuideGender() == 2? ContextCompat.getDrawable(mContext,R.mipmap.icon_girl):ContextCompat.getDrawable(mContext,R.mipmap.icon_boy));

        }

    }

    @Override
    public int getItemCount() {
        return guideDatas.size();
    }

    public void addData(List<GuideModel> guideDatas) {
        this.guideDatas.addAll(guideDatas);
        notifyDataSetChanged();
    }

    public void setData(List<GuideModel> guideDatas) {
        this.guideDatas = guideDatas;
        notifyDataSetChanged();
    }

    public List<GuideModel> getDatas(){
        return this.guideDatas;
    }

    public static class GuideViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cardView;
        ImageView iv_head,iv_sex,iv_backimg;
        TextView tv_name;
        MyRatingBar rating_bar_route;
        TextView tv_content;
        ServiceTagView tv_service_item;
        GuideScoreView ll_times;

        GuideViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            iv_head = itemView.findViewById(R.id.iv_head);
            iv_backimg = itemView.findViewById(R.id.iv_backimg);
            tv_name = itemView.findViewById(R.id.tv_name);
            rating_bar_route = itemView.findViewById(R.id.rating_bar_route);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_service_item = itemView.findViewById(R.id.tv_service_item);
            ll_times = itemView.findViewById(R.id.ll_times);
            iv_sex = itemView.findViewById(R.id.iv_sex);
        }
    }


    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
