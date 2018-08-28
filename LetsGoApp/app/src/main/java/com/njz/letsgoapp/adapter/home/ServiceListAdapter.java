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
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.widget.PriceView;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {

    private Context context;
    private List<ServiceItem> serviceItems;

    public ServiceListAdapter(Context context, List<ServiceItem> serviceItems) {
        this.context = context;
        this.serviceItems = serviceItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) return;
        final int pos = holder.getAdapterPosition();
        final ServiceItem data = serviceItems.get(pos);
        if (data == null) return;

        GlideUtil.LoadImage(context, data.getImg(), ((ViewHolder) holder).iv_img);
        ((ViewHolder) holder).iv_img_content.setText("代订 | 张家界");
        ((ViewHolder) holder).tv_title.setText(data.getContent());
        ((ViewHolder) holder).tv_sell.setText("已售" + 112);
        ((ViewHolder) holder).pv_price.setPrice(625d);


        if (mOnItemClickListener != null) {
            ((ViewHolder) holder).rl_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(pos);
                }
            });
        }
    }

    public void setData(List<ServiceItem> serviceItems){
        this.serviceItems = serviceItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return serviceItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_img;
        TextView iv_img_content, tv_title, tv_sell, btn_event;
        PriceView pv_price;
        RelativeLayout rl_parent;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_img_content = itemView.findViewById(R.id.iv_img_content);
            tv_sell = itemView.findViewById(R.id.tv_score);
            btn_event = itemView.findViewById(R.id.btn_event);
            pv_price = itemView.findViewById(R.id.pv_price);
            rl_parent = itemView.findViewById(R.id.rl_parent);

        }
    }

    HomeAdapter.OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(HomeAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
