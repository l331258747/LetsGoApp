package com.njz.letsgoapp.adapter.home;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.bean.home.ServiceListModel;
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
    private List<ServiceListModel> serviceListModels;
    private List<ServiceItem> serviceItems;

    public ServiceListAdapter(Context context, List<ServiceListModel> serviceListModels,List<ServiceItem> serviceItems) {
        this.context = context;
        this.serviceListModels = serviceListModels;
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
        final ServiceListModel data = serviceListModels.get(pos);
        if (data == null) return;

        GlideUtil.LoadImage(context, null, holder.iv_img);
        holder.iv_img_content.setText(data.getServiceType());
        holder.tv_title.setText(data.getTitle());
        holder.tv_sell.setText(data.getCount());
        holder.pv_price.setPrice(data.getServePrice());

        if (mOnItemClickListener != null) {
            holder.rl_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(pos);
                }
            });
            holder.btn_event.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onBtnClick(pos);
                }
            });
        }

        if(isServiceSelected(data)){
            holder.btn_event.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_cc_solid_r5));
            holder.btn_event.setTextColor(ContextCompat.getColor(context,R.color.color_text));
            holder.btn_event.setEnabled(false);
        }else{
            holder.btn_event.setBackground(ContextCompat.getDrawable(context,R.drawable.btn_theme_solid_r5));
            holder.btn_event.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.btn_event.setEnabled(true);
        }
    }

    public void setData(List<ServiceListModel> serviceListModels){
        this.serviceListModels = serviceListModels;
        notifyDataSetChanged();
    }

    public boolean isServiceSelected(ServiceListModel serviceListModel){
        if(serviceItems == null) return false;
        for (ServiceItem item : serviceItems){
            if(item.getId() == serviceListModel.getId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return serviceListModels.size();
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

    OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onBtnClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
