package com.njz.letsgoapp.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.njz.letsgoapp.bean.home.HomeData;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.other.BigImageActivity;
import com.njz.letsgoapp.widget.DynamicImageView;
import com.njz.letsgoapp.widget.GuideScoreView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;
import com.njz.letsgoapp.widget.myTextView.ShowAllTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BaseViewHolder> {

    public static final int VIEW_TITLE = 1;

    List<HomeData.Dynamic> guides;

    Context mContext;

    public HomeAdapter(Context context, HomeData homeData) {
        this.guides = homeData.getGuides();
        this.mContext = context;
    }

    @Override
    public HomeAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_TITLE:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_guide_title, parent, false);
                return new GuideTitleViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_dynamic, parent, false);
                return new DynamicViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TITLE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof DynamicViewHolder) {
            final int pos = holder.getAdapterPosition() - 1;
            final HomeData.Dynamic data = guides.get(pos);
            if (data == null) return;

            GlideUtil.LoadCircleImage(mContext, data.getHeadImg(), ((DynamicViewHolder) holder).iv_img);
            ((DynamicViewHolder) holder).tv_name.setText(data.getName());
            ((DynamicViewHolder) holder).tv_time.setText(data.getTime());
            ((DynamicViewHolder) holder).tv_location.setText(data.getLocation());
            ((DynamicViewHolder) holder).tv_comment.setText(""+data.getComment());
            ((DynamicViewHolder) holder).tv_nice.setText(""+data.getNice());

            ((DynamicViewHolder) holder).tv_content.setMaxShowLines(3);
            ((DynamicViewHolder) holder).tv_content.setMyText(""+data.getContent());

            ((DynamicViewHolder) holder).dynamic_image_view.setImages(data.getDynamicImgs());
            ((DynamicViewHolder) holder).dynamic_image_view.setOnItemClickListener(new DynamicImageView.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    BigImageActivity.startActivity((Activity) mContext,position,data.getDynamicImgs());
                }
            });

            if (mOnItemClickListener != null) {
                ((DynamicViewHolder) holder).ll_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(pos);
                    }
                });
            }
//        if (holder instanceof HomeGuideViewHolder) {
//            final int pos = holder.getAdapterPosition() - 1;
//            final HomeData.Guide data = guides.get(pos);
//            if (data == null) return;
//
//            GlideUtil.LoadImage(mContext, data.getBackgroundImg(), ((HomeGuideViewHolder) holder).iv_backGround);
//            GlideUtil.LoadCircleImage(mContext, data.getHeadImg(), ((HomeGuideViewHolder) holder).iv_head);
//            ((HomeGuideViewHolder) holder).tv_name.setText(data.getName());
//            ((HomeGuideViewHolder) holder).rating_bar_route.setRating(data.getLevel());
//            ((HomeGuideViewHolder) holder).tv_content.setText(data.getContent());
//            ((HomeGuideViewHolder) holder).serviceTagView.setServiceTag(data.getServiceTags());
//            ((HomeGuideViewHolder) holder).ll_times.setGuideScore(data.getTimes(), data.getLevel(), data.getCommentTimes());
//
//            if (mOnItemClickListener != null) {
//                ((HomeGuideViewHolder) holder).rlParent.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mOnItemClickListener.onClick(pos);
//                    }
//                });
//            }
        }
    }

    @Override
    public int getItemCount() {
        if (guides == null || guides.size() == 0) {
            return 0;
        } else {
            return 1 + guides.size();
        }
    }

    //--------------View Holder start----------------
    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class DynamicViewHolder extends BaseViewHolder {
        LinearLayout ll_parent;
        ImageView iv_img;
        TextView tv_name,tv_time,tv_location,tv_comment,tv_nice;
        DynamicImageView dynamic_image_view;
        ShowAllTextView  tv_content;

        DynamicViewHolder(View itemView) {
            super(itemView);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_nice = itemView.findViewById(R.id.tv_nice);
            dynamic_image_view = itemView.findViewById(R.id.dynamic_image_view);
        }

    }


    public class HomeGuideViewHolder extends BaseViewHolder {
        RelativeLayout rlParent;
        ImageView iv_backGround;
        ImageView iv_head;
        TextView tv_name;
        MyRatingBar rating_bar_route;
        TextView tv_content;
        ServiceTagView serviceTagView;

        GuideScoreView ll_times;

        HomeGuideViewHolder(View itemView) {
            super(itemView);
            iv_backGround = itemView.findViewById(R.id.iv_backGround);
            rlParent = itemView.findViewById(R.id.rl_parent);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            rating_bar_route = itemView.findViewById(R.id.rating_bar_route);
            tv_content = itemView.findViewById(R.id.tv_content);
            serviceTagView = itemView.findViewById(R.id.tv_service_item);
            ll_times = itemView.findViewById(R.id.ll_times);
        }
    }

    public class GuideTitleViewHolder extends BaseViewHolder {
        TextView tv_check_all;

        GuideTitleViewHolder(View itemView) {
            super(itemView);
            tv_check_all = itemView.findViewById(R.id.tv_check_all);
            tv_check_all.setOnClickListener(onCheckAllListener);
        }
    }


    //--------------View Holder end----------------


    public void setData(HomeData homeData) {
        this.guides = homeData.getGuides();
        notifyDataSetChanged();
    }

    //---------事件 start---------
    OnItemClickListener mOnItemClickListener;
    View.OnClickListener onCheckAllListener;


    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setCheckAllListener(View.OnClickListener onCheckAllListener) {
        this.onCheckAllListener = onCheckAllListener;
    }
    //---------事件 end---------


}
