package com.njz.letsgoapp.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.util.log.LogUtil;
import com.njz.letsgoapp.view.other.BigImageActivity;
import com.njz.letsgoapp.widget.DynamicImageView;
import com.njz.letsgoapp.widget.myTextView.ShowAllTextView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.BaseViewHolder> {

    public static final int VIEW_TITLE = 1;

    List<DynamicModel> dynamis;
    int type;//0为首页，1位个人主页,2无标题

    Context mContext;

    public DynamicAdapter(Context context, List<DynamicModel> dynamis) {
        this(context,dynamis,0);
    }

    public DynamicAdapter(Context context, List<DynamicModel> dynamis,int type) {
        this.dynamis = dynamis;
        this.mContext = context;
        this.type = type;
    }

    @Override
    public DynamicAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(type == 2){
            view = LayoutInflater.from(mContext).inflate(R.layout.home_item_dynamic, parent, false);
            return new DynamicViewHolder(view);
        }
        switch (viewType) {
            case VIEW_TITLE:
                if(type == 1){
                    view = LayoutInflater.from(mContext).inflate(R.layout.home_item_guide_title2, parent, false);
                    return new GuideTitleViewHolder2(view);
                }
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_guide_title, parent, false);
                return new GuideTitleViewHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.home_item_dynamic, parent, false);
                return new DynamicViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(type == 2){
            return super.getItemViewType(position);
        }
        if (position == 0) {
            return VIEW_TITLE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (holder == null) return;
        if (holder instanceof DynamicViewHolder) {
            final int pos;
            if(type == 2){
                pos = holder.getAdapterPosition();
            }else{
                pos = holder.getAdapterPosition() - 1;
            }

            final DynamicModel data = dynamis.get(pos);
            if (data == null) return;

            GlideUtil.LoadCircleImage(mContext, data.getImgUrl(), ((DynamicViewHolder) holder).iv_img);
            ((DynamicViewHolder) holder).tv_name.setText(data.getNickname());
            ((DynamicViewHolder) holder).tv_time.setText(data.getStartTime());
            ((DynamicViewHolder) holder).tv_location.setText(data.getLocation());
            ((DynamicViewHolder) holder).tv_comment.setText(""+data.getReplyCount());
            ((DynamicViewHolder) holder).tv_nice.setText(""+data.getLikeCount());

            if(data.isLike()){
                ((DynamicViewHolder) holder).tv_nice.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext,R.mipmap.ic_dynamic_nice),null,null,null);
            }else{
                ((DynamicViewHolder) holder).tv_nice.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(mContext,R.mipmap.ic_dynamic_nice_un),null,null,null);
            }

            ((DynamicViewHolder) holder).tv_content.setMaxShowLines(3);
            ((DynamicViewHolder) holder).tv_content.setMyText(""+data.getContent());

            ((DynamicViewHolder) holder).dynamic_image_view.setImages(data.getImgUrls());
            ((DynamicViewHolder) holder).dynamic_image_view.setOnItemClickListener(new DynamicImageView.OnItemClickListener() {
                @Override
                public void onClick(int position) {
                    BigImageActivity.startActivity((Activity) mContext,position,data.getImgUrls());
                }
            });

            if(mNiceClickListener != null){
                ((DynamicViewHolder) holder).tv_nice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mNiceClickListener.onClick(pos);
                    }
                });
            }

            if (mOnItemClickListener != null) {
                ((DynamicViewHolder) holder).ll_parent.setOnClickListener(new View.OnClickListener() {
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
        if (dynamis == null || dynamis.size() == 0) {
            return 0;
        } else {
            if(type == 2){
                return dynamis.size();
            }
            return 1 + dynamis.size();
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
            tv_comment = itemView.findViewById(R.id.tv_count);
            tv_nice = itemView.findViewById(R.id.tv_nice);
            dynamic_image_view = itemView.findViewById(R.id.dynamic_image_view);
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

    public class GuideTitleViewHolder2 extends BaseViewHolder {
        GuideTitleViewHolder2(View itemView) {
            super(itemView);
        }
    }


    //--------------View Holder end----------------


    public void setData(List<DynamicModel> dynamis) {
        this.dynamis = dynamis;
    }

    public void setItemData(int position){
        if(type == 2){
            notifyItemChanged(position);
        }else{
            notifyItemChanged(position + 1);
        }
    }

    public void addData(List<DynamicModel> dynamis){
        this.dynamis.addAll(dynamis);
    }

    //---------事件 start---------
    OnItemClickListener mOnItemClickListener;
    OnItemClickListener mNiceClickListener;
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

    public void setNiceClickListener(OnItemClickListener mNiceClickListener){
        this.mNiceClickListener = mNiceClickListener;
    }

    //---------事件 end---------


}
