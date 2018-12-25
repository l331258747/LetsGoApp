package com.njz.letsgoapp.adapter.other;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.GuideLabelView;
import com.njz.letsgoapp.widget.MyRatingBar;
import com.njz.letsgoapp.widget.ServiceTagView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/25
 * Function:
 */

public class GuideSearchAdapter extends RecyclerView.Adapter<GuideSearchAdapter.ViewHolder> {

    Context context;
    List<GuideModel> datas;

    public GuideSearchAdapter(Context context, List<GuideModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_guide_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;
        final GuideModel data = datas.get(position);
        if (data == null) return;

        GlideUtil.LoadCircleImage(context,data.getGuideImg(),holder.iv_head);
        holder.iv_sex.setImageDrawable(ContextCompat.getDrawable(context,data.getGuideGender() == 2?R.mipmap.icon_girl:R.mipmap.icon_boy));
        holder.tv_name.setText(data.getGuideName());
        holder.tv_service_num.setText("服务" + data.getServiceCounts() + "次");
        holder.myRatingBar.setRating((int) data.getGuideScore());
        holder.guideLabelView.setTabel(new ArrayList<String>());
        holder.serviceTagView.setServiceTag(new ArrayList<String>());

        holder.ll_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setDatas(List<GuideModel> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<GuideModel> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public GuideModel getData(int position){
        return this.datas.get(position);
    }

    public List<GuideModel> getDatas(){
        return this.datas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_head,iv_sex;
        TextView tv_name,tv_service_num;
        MyRatingBar myRatingBar;
        GuideLabelView guideLabelView;
        ServiceTagView serviceTagView;
        LinearLayout ll_parent;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_head = itemView.findViewById(R.id.iv_head);
            iv_sex = itemView.findViewById(R.id.iv_sex);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_service_num = itemView.findViewById(R.id.tv_service_num);
            myRatingBar = itemView.findViewById(R.id.my_rating_bar);
            guideLabelView = itemView.findViewById(R.id.guide_label);
            serviceTagView = itemView.findViewById(R.id.stv_tag);
            ll_parent = itemView.findViewById(R.id.ll_parent);
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
