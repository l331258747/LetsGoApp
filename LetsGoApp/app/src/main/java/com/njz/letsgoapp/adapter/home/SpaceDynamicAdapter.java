package com.njz.letsgoapp.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.DynamicModel;
import com.njz.letsgoapp.view.other.BigImageActivity;
import com.njz.letsgoapp.widget.DynamicSpaceImageView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/6
 * Function:
 */

public class SpaceDynamicAdapter extends RecyclerView.Adapter<SpaceDynamicAdapter.BaseViewHolder> {

    List<DynamicModel> dynamis;

    Context mContext;

    public SpaceDynamicAdapter(Context context, List<DynamicModel> dynamis) {
        this.dynamis = dynamis;
        this.mContext = context;
    }

    @Override
    public SpaceDynamicAdapter.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.home_item_space_dynamic, parent, false);
        return new DynamicViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dynamis.size();
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        if (holder == null) return;
        if (holder instanceof DynamicViewHolder) {
            final int pos = holder.getAdapterPosition();
            final DynamicModel data = dynamis.get(pos);
            if (data == null) return;

            ((DynamicViewHolder) holder).tv_time.setVisibility(View.GONE);
            if (pos == 0) {
                ((DynamicViewHolder) holder).tv_time.setVisibility(View.VISIBLE);
                ((DynamicViewHolder) holder).tv_time.setText(data.getStartTimeTwo());
            } else {
                if (TextUtils.equals(dynamis.get(pos).getStartTimeTwo(), dynamis.get(pos - 1).getStartTimeTwo())) {
                    ((DynamicViewHolder) holder).tv_time.setVisibility(View.GONE);
                } else {
                    ((DynamicViewHolder) holder).tv_time.setVisibility(View.VISIBLE);
                    ((DynamicViewHolder) holder).tv_time.setText(data.getStartTimeTwo());
                }
            }

            if (data.getImgUrls() == null || data.getImgUrls().size() == 0) {
                ((DynamicViewHolder) holder).ll_text_content.setVisibility(View.VISIBLE);
                ((DynamicViewHolder) holder).ll_img_content.setVisibility(View.GONE);
            } else {
                ((DynamicViewHolder) holder).ll_text_content.setVisibility(View.GONE);
                ((DynamicViewHolder) holder).ll_img_content.setVisibility(View.VISIBLE);
            }

            ((DynamicViewHolder) holder).tv_content_2.setText(""+data.getContent());
            ((DynamicViewHolder) holder).dynamic_image_view.setImages(data.getImgUrls());
            ((DynamicViewHolder) holder).tv_content.setText("" + data.getContent());
            ((DynamicViewHolder) holder).tv_img_count.setText("共" + data.getImgUrls().size() + "张");

            ((DynamicViewHolder) holder).dynamic_image_view.setOnItemClickListener(new DynamicSpaceImageView.OnItemClickListener() {
                @Override
                public void onClick() {
                    BigImageActivity.startActivity((Activity) mContext, 0, data.getImgUrls());
                }
            });

            if (mOnItemClickListener != null) {
                ((DynamicViewHolder) holder).ll_parent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(pos);
                    }
                });
            }
        }
    }

    //--------------View Holder start----------------
    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class DynamicViewHolder extends BaseViewHolder {
        RelativeLayout ll_parent;
        DynamicSpaceImageView dynamic_image_view;
        TextView tv_content, tv_img_count, tv_time,tv_content_2;
        LinearLayout ll_text_content, ll_img_content;

        DynamicViewHolder(View itemView) {
            super(itemView);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            dynamic_image_view = itemView.findViewById(R.id.dynamic_image_view);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_img_count = itemView.findViewById(R.id.tv_img_count);
            tv_time = itemView.findViewById(R.id.tv_time);
            ll_text_content = itemView.findViewById(R.id.ll_text_content);
            ll_img_content = itemView.findViewById(R.id.ll_img_content);
            tv_content_2 = itemView.findViewById(R.id.tv_content_2);
        }

    }

    //--------------View Holder end----------------

    public DynamicModel getItem(int position) {
        return this.dynamis.get(position);
    }

    public List<DynamicModel> getDatas() {
        return this.dynamis;
    }

    public void setData(List<DynamicModel> dynamis) {
        this.dynamis = dynamis;
        notifyDataSetChanged();
    }

    public void addData(List<DynamicModel> dynamis) {
        this.dynamis.addAll(dynamis);
        notifyDataSetChanged();
    }

    //---------事件 start---------
    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    //---------事件 end---------


}
