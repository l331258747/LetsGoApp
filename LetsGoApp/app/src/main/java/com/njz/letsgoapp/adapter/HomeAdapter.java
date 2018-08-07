package com.njz.letsgoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.HomeData;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.cityPick.CityPickActivity;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.PriceView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.BaseViewHolder> {

    List<HomeData.HomeBanner> homeBanners;
    List<HomeData.Guide> guides;
    Context mContext;

    private static final int HOME_VIEW_TYPE_BANNER = 10;
    private static final int HOME_VIEW_TYPE_TRIPSETTING = 11;
    private static final int HOME_VIEW_TYPE_GUIDE_TIME = 12;


    public HomeAdapter(Context context, List<HomeData.HomeBanner> homeBanners, List<HomeData.Guide> guides) {
        this.homeBanners = homeBanners;
        this.guides = guides;
        this.mContext = context;

    }

    @Override
    public HomeAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HOME_VIEW_TYPE_BANNER:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_banner, parent, false);
                return new HomeBannerViewHolder(view,homeBanners);
            case HOME_VIEW_TYPE_TRIPSETTING:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_trip_setting, parent, false);
                return new HomeTripSettingViewHolder(view);
            case HOME_VIEW_TYPE_GUIDE_TIME:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_guide_title, parent, false);
                return new HomeGuideTitleViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_guide, parent, false);
                return new HomeGuideViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HOME_VIEW_TYPE_BANNER;
        if (position == 1)
            return HOME_VIEW_TYPE_TRIPSETTING;
        if (position == 2)
            return HOME_VIEW_TYPE_GUIDE_TIME;
        return super.getItemViewType(position);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof HomeGuideViewHolder) {
            final int pos = holder.getAdapterPosition() - 3;
            final HomeData.Guide data = guides.get(pos);
            if (data == null) return;

            GlideUtil.LoadImage(mContext, data.getBackgroundImg(), ((HomeGuideViewHolder) holder).iv_backGround);
            GlideUtil.LoadCircleImage(mContext, data.getHeadImg(), ((HomeGuideViewHolder) holder).iv_head);
            ((HomeGuideViewHolder) holder).tv_name.setText(data.getName());
            ((HomeGuideViewHolder) holder).rating_bar_route.setRating(data.getLevel());
            ((HomeGuideViewHolder) holder).tv_content.setText(data.getContent());
            ((HomeGuideViewHolder) holder).tv_price1.setPrice(data.getPrice());
        }


        if (holder instanceof HomeBannerViewHolder) {

        }

        if (holder instanceof HomeTripSettingViewHolder) {

        }

        if (holder instanceof HomeGuideTitleViewHolder) {

        }
    }

    @Override
    public int getItemCount() {
        return guides == null ? 3 : 3 + guides.size();
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class HomeGuideTitleViewHolder extends BaseViewHolder {

        TextView tvCheckAll;

        HomeGuideTitleViewHolder(View itemView) {
            super(itemView);
            tvCheckAll = itemView.findViewById(R.id.tv_check_all);
            tvCheckAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShortToast(mContext,"查看全部");
                }
            });
        }
    }

    public static class HomeGuideViewHolder extends BaseViewHolder {
        ImageView iv_backGround;
        ImageView iv_head;
        TextView tv_name;
        MyRatingBar rating_bar_route;
        TextView tv_content;
        PriceView tv_price1;

        HomeGuideViewHolder(View itemView) {
            super(itemView);
            iv_backGround = itemView.findViewById(R.id.iv_backGround);
            iv_head = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            rating_bar_route = itemView.findViewById(R.id.rating_bar_route);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_price1 = itemView.findViewById(R.id.tv_price1);
        }
    }

    View.OnClickListener destinationListener;
    public void setDestinationListener(View.OnClickListener destinationListener){
        this.destinationListener = destinationListener;
    }

    public class HomeTripSettingViewHolder extends BaseViewHolder {

        HomeTripSettingViewHolder(View itemView) {
            super(itemView);
            TextView tv_destination_content = itemView.findViewById(R.id.tv_destination_content);
            TextView tv_start_time_content = itemView.findViewById(R.id.tv_start_time_content);
            TextView tv_end_time_content = itemView.findViewById(R.id.tv_end_time_content);
            TextView tv_day_time = itemView.findViewById(R.id.tv_day_time);
            TextView btn_trip_setting = itemView.findViewById(R.id.btn_trip_setting);

            tv_destination_content.setOnClickListener(destinationListener);

        }
    }


    public class HomeBannerViewHolder extends BaseViewHolder {
        ConvenientBanner convenientBanner;

        HomeBannerViewHolder(View itemView, List<HomeData.HomeBanner> homeBanners) {
            super(itemView);

            convenientBanner = itemView.findViewById(R.id.convenientBanner);

            if (homeBanners.isEmpty()) return;

            //开始自动翻页
            convenientBanner.setPages(new CBViewHolderCreator() {
                @Override
                public Object createHolder() {
                    return new LocalImageHolderView();
                }
            }, homeBanners)
                    .setPointViewVisible(true) //设置指示器是否可见
                    .startTurning(4000)//设置自动切换（同时设置了切换时间间隔）
                    .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})//设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)//设置指示器的方向（左、中、右）
//                    .setOnItemClickListener(this) //设置点击监听事件
                    .setManualPageable(true);//设置手动影响（设置了该项无法手动切换）

        }
    }

    //为了方便改写，来实现复杂布局的切换
    public class LocalImageHolderView implements Holder<HomeData.HomeBanner> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            //你可以通过layout文件来创建，不一定是Image，任何控件都可以进行翻页
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, HomeData.HomeBanner data) {
            GlideUtil.LoadImage(context, data.getImgUrl(), imageView);
        }
    }


}
