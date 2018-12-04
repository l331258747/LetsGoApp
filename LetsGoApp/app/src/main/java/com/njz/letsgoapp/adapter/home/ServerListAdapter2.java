package com.njz.letsgoapp.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.PlayData;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.GuideScoreView2;
import com.njz.letsgoapp.widget.PriceView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/4
 * Function:
 */

public class ServerListAdapter2 extends RecyclerView.Adapter<ServerListAdapter2.ViewHolder>{
    List<PlayData> datas;
    Context context;

    public ServerListAdapter2(Context context,List<PlayData> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_server_list_2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;
        PlayData data = datas.get(position);
        GlideUtil.LoadImage(context,data.getImg(),holder.iv_img);
        holder.tv_location.setText(data.getLocation());
        holder.tv_title.setText(data.getTitle());
        holder.guideScoreView2.setGuideScore(data.getCount(),data.getScore(),data.getComment());
        holder.priceView.setPrice(data.getPrice());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_img;
        TextView tv_location,tv_title;
        GuideScoreView2 guideScoreView2;
        PriceView priceView;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_title = itemView.findViewById(R.id.tv_title);
            guideScoreView2 = itemView.findViewById(R.id.guideScoreView2);
            priceView = itemView.findViewById(R.id.priceView);
        }

    }

    public void setDatas(List<PlayData> datas){
        this.datas = datas;
        if(datas == null) return;

        notifyDataSetChanged();
    }

    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
