package com.njz.letsgoapp.adapter.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/23
 * Function:
 */

public class HomeGuideAdapter extends RecyclerView.Adapter<HomeGuideAdapter.ViewHolder> {

    Context context;
    List<GuideModel> datas;

    public HomeGuideAdapter(Context context, List<GuideModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;
        final GuideModel data = datas.get(position);
        if (data == null) return;

        GlideUtil.LoadRoundImage(context, data.getGuideImg(), holder.iv_img);
        holder.tv_name.setText(data.getGuideName());
        holder.iv_sex.setImageDrawable(data.getGuideGender() == 0?ContextCompat.getDrawable(context,R.mipmap.icon_girl):ContextCompat.getDrawable(context,R.mipmap.icon_boy));
        holder.tv_service_num.setText(""+data.getCount());
        if (mOnItemClickListener != null) {
            holder.cv_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    public void setData(List<GuideModel> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_img,iv_sex;
        TextView tv_name,tv_service_num;
        CardView cv_parent;


        public ViewHolder(View itemView) {
            super(itemView);

            iv_img = itemView.findViewById(R.id.iv_img);
            iv_sex = itemView.findViewById(R.id.iv_sex);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_service_num = itemView.findViewById(R.id.tv_service_num);
            cv_parent = itemView.findViewById(R.id.cv_parent);
        }
    }

    GuideListAdapter.OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(GuideListAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
