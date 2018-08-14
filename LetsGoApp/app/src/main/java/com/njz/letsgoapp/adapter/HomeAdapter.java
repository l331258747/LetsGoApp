package com.njz.letsgoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.HomeData;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.banner.LocalImageHolderView;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.PriceView;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BaseViewHolder> {

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
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_guide, parent, false);
                return new HomeGuideViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof HomeGuideViewHolder) {
            final int pos = holder.getAdapterPosition();
            final HomeData.Guide data = guides.get(pos);
            if (data == null) return;

            GlideUtil.LoadImage(mContext, data.getBackgroundImg(), ((HomeGuideViewHolder) holder).iv_backGround);
            GlideUtil.LoadCircleImage(mContext, data.getHeadImg(), ((HomeGuideViewHolder) holder).iv_head);
            ((HomeGuideViewHolder) holder).tv_name.setText(data.getName());
            ((HomeGuideViewHolder) holder).rating_bar_route.setRating(data.getLevel());
            ((HomeGuideViewHolder) holder).tv_content.setText(data.getContent());
            ((HomeGuideViewHolder) holder).tv_price1.setPrice(data.getPrice());
            ((HomeGuideViewHolder) holder).serviceTagView.setServiceTag(data.getServiceTags());

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
            return guides.size();
        }
    }

    //--------------View Holder start----------------
    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class HomeGuideViewHolder extends BaseViewHolder {
        RelativeLayout rlParent;
        ImageView iv_backGround;
        ImageView iv_head;
        TextView tv_name;
        MyRatingBar rating_bar_route;
        TextView tv_content;
        PriceView tv_price1;
        ServiceTagView serviceTagView;

        HomeGuideViewHolder(View itemView) {
            super(itemView);
            iv_backGround = itemView.findViewById(R.id.iv_backGround);
            rlParent = itemView.findViewById(R.id.rl_parent);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            rating_bar_route = itemView.findViewById(R.id.rating_bar_route);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_price1 = itemView.findViewById(R.id.tv_price1);
            serviceTagView = itemView.findViewById(R.id.tv_service_item);
        }
    }


    //--------------View Holder end----------------


    public void setData(HomeData homeData) {
        this.guides = homeData.getGuides();
        notifyDataSetChanged();
    }

    //---------事件 start---------
    OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    //---------事件 end---------


}
