package com.njz.letsgoapp.adapter.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.server.ServerDetailMedel;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.GuideScoreView2;
import com.njz.letsgoapp.widget.PriceView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/3
 * Function:
 */

public class HomeServerAdapter extends RecyclerView.Adapter<HomeServerAdapter.ViewHolder> {

    Context context;
    List<ServerDetailMedel> datas;

    public HomeServerAdapter(Context context, List<ServerDetailMedel> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_play, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder == null) return;
        final ServerDetailMedel data = datas.get(position);
        if (data == null) return;

        GlideUtil.LoadTopRoundImage(context, data.getTitleImg(), holder.iv_img,5);
        holder.tv_title.setText(data.getTitle());
        holder.tv_location.setText(data.getAddress());
        holder.priceView.setPrice(data.getServePrice());
        holder.scoreView.setGuideScore(data.getSellCount(),data.getScore(),data.getReviewCount());
        if (mOnItemClickListener != null) {
            holder.ll_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<ServerDetailMedel> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void addData(List<ServerDetailMedel> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public ServerDetailMedel getData(int position){
        return this.datas.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_parent;
        ImageView iv_img;
        TextView tv_title,tv_location;
        PriceView priceView;
        GuideScoreView2 scoreView;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_location = itemView.findViewById(R.id.tv_location);
            priceView = itemView.findViewById(R.id.priceView);
            scoreView = itemView.findViewById(R.id.scoreView);
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
