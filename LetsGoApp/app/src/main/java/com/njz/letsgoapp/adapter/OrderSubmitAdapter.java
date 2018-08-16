package com.njz.letsgoapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.GuideData;
import com.njz.letsgoapp.bean.home.ServiceInfo;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class OrderSubmitAdapter extends RecyclerView.Adapter<OrderSubmitAdapter.BaseViewHolder> {

    private Context context;
    private List<ServiceItem> serviceItems;

    public static final int VIEW_2 = 1;

    public OrderSubmitAdapter(Context context, List<ServiceItem> data) {
        this.context = context;
        this.serviceItems = data;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case VIEW_2:
                view = LayoutInflater.from(context).inflate(R.layout.item_order_submit_2, parent, false);
                return new OrderSubmitAdapter.ViewHolder2(view);
            default:
                view = LayoutInflater.from(context).inflate(R.layout.item_order_submit, parent, false);
                return new OrderSubmitAdapter.ViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) return;
        if(holder instanceof ViewHolder){
            final int pos = holder.getAdapterPosition();
            final ServiceItem data = serviceItems.get(pos);
            if (data == null) return;

            GlideUtil.LoadImage(context, data.getImg(), ((ViewHolder) holder).iv_img);
            ((ViewHolder) holder).tv_title.setText(data.getContent());
            ((ViewHolder) holder).tv_price_unit.setText("￥" + data.getPrice());
            ((ViewHolder) holder).tv_day.setText("4天");
            ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * 4);
        }


        if(holder instanceof ViewHolder2){
            final int pos = holder.getAdapterPosition();
            final ServiceItem data = serviceItems.get(pos);
            if (data == null) return;

            GlideUtil.LoadImage(context, data.getImg(), ((ViewHolder2) holder).iv_img);
            ((ViewHolder2) holder).tv_title.setText(data.getContent());
            ((ViewHolder2) holder).tv_price_unit.setText("￥" + data.getPrice());
            ((ViewHolder2) holder).tv_day.setText("4天");
            ((ViewHolder2) holder).tv_price_total.setText("￥" + data.getPrice() * 4);
            ((ViewHolder2) holder).tv_time_title.setText("入住时间");
            ((ViewHolder2) holder).tv_time_start.setText("2018-07-14");
            ((ViewHolder2) holder).tv_time_end.setText("2018-07-18");

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position>1){
            return VIEW_2;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return serviceItems.size();
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolder extends BaseViewHolder {

        ImageView iv_img;
        TextView tv_title, tv_price_unit, tv_day, tv_price_total;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price_unit = itemView.findViewById(R.id.tv_price_unit);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_price_total = itemView.findViewById(R.id.tv_price_total);

        }
    }
    public class ViewHolder2 extends BaseViewHolder {

        ImageView iv_img;
        TextView tv_title, tv_price_unit, tv_day, tv_price_total;
        View view_line;
        RelativeLayout rl_time;
        TextView tv_time_title,tv_time_start,tv_time_end;

        public ViewHolder2(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price_unit = itemView.findViewById(R.id.tv_price_unit);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_price_total = itemView.findViewById(R.id.tv_price_total);
            view_line = itemView.findViewById(R.id.view_line);
            rl_time = itemView.findViewById(R.id.rl_time);
            tv_time_title = itemView.findViewById(R.id.tv_time_title);
            tv_time_start = itemView.findViewById(R.id.tv_time_start);
            tv_time_end = itemView.findViewById(R.id.tv_time_end);
        }
    }

}
