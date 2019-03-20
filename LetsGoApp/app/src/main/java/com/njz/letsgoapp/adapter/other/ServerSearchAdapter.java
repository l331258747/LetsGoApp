package com.njz.letsgoapp.adapter.other;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.GuideModel;
import com.njz.letsgoapp.bean.server.ServerDetailModel;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.GuideScoreView2;
import com.njz.letsgoapp.widget.PriceView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/25
 * Function:
 */

public class ServerSearchAdapter extends RecyclerView.Adapter<ServerSearchAdapter.ViewHolder> {

    Context context;
    List<ServerDetailModel> datas;

    public ServerSearchAdapter(Context context, List<ServerDetailModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_server_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;
        final ServerDetailModel data = datas.get(position);
        if (data == null) return;

        GlideUtil.LoadImage(context,data.getTitleImg(),holder.iv_img);
        holder.tv_location.setText(data.getAddress());
        holder.tv_title.setText(data.getTitle());
        holder.guideScoreView2.setGuideScore(data.getSellCount(),data.getScore(),data.getReviewCount());
        holder.priceView.setPrice(data.getServePrice());
        holder.tv_serverType.setText(data.getServerName());

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

    public void setDatas(List<ServerDetailModel> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addDatas(List<ServerDetailModel> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public ServerDetailModel getData(int position){
        return this.datas.get(position);
    }

    public List<ServerDetailModel> getDatas(){
        return this.datas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_parent;
        ImageView iv_img;
        TextView tv_location,tv_title,tv_serverType;
        GuideScoreView2 guideScoreView2;
        PriceView priceView;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_serverType = itemView.findViewById(R.id.tv_serverType);
            guideScoreView2 = itemView.findViewById(R.id.guideScoreView2);
            priceView = itemView.findViewById(R.id.priceView);
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
