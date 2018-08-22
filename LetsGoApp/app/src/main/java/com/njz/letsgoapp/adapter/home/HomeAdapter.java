package com.njz.letsgoapp.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.HomeData;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.GuideScoreView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BaseViewHolder> {

    public static final int VIEW_TITLE = 1;

    List<HomeData.Guide> guides;

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
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_guide, parent, false);
                return new HomeGuideViewHolder(view);
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
        if (holder instanceof HomeGuideViewHolder) {
            final int pos = holder.getAdapterPosition() - 1;
            final HomeData.Guide data = guides.get(pos);
            if (data == null) return;

            GlideUtil.LoadImage(mContext, data.getBackgroundImg(), ((HomeGuideViewHolder) holder).iv_backGround);
            GlideUtil.LoadCircleImage(mContext, data.getHeadImg(), ((HomeGuideViewHolder) holder).iv_head);
            ((HomeGuideViewHolder) holder).tv_name.setText(data.getName());
            ((HomeGuideViewHolder) holder).rating_bar_route.setRating(data.getLevel());
            ((HomeGuideViewHolder) holder).tv_content.setText(data.getContent());
            ((HomeGuideViewHolder) holder).serviceTagView.setServiceTag(data.getServiceTags());
            ((HomeGuideViewHolder) holder).ll_times.setGuideScore(data.getTimes(), data.getLevel(), data.getCommentTimes());

            if (mOnItemClickListener != null) {
                ((HomeGuideViewHolder) holder).rlParent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(pos);
                    }
                });
            }
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
