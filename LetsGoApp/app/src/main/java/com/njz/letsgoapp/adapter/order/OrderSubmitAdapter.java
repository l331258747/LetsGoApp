package com.njz.letsgoapp.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.constant.Constant;
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

    public OrderSubmitAdapter(Context context, List<ServiceItem> data) {
        this.context = context;
        this.serviceItems = data;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_order_submit, parent, false);
        return new OrderSubmitAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof ViewHolder) {
            final int pos = holder.getAdapterPosition();
            final ServiceItem data = serviceItems.get(pos);
            if (data == null) return;

            GlideUtil.LoadImage(context, data.getImg(), ((ViewHolder) holder).iv_img);
            ((ViewHolder) holder).tv_title.setText(data.getTitile());
            ((ViewHolder) holder).tv_price_unit.setText("￥" + data.getPrice());

            switch (data.getServeType()) {
                case Constant.SERVICE_TYPE_CUSTOM:
                    ((ViewHolder) holder).tv_day.setText("x" + data.getNumber() + "人");
                    ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * data.getNumber());

                    ((ViewHolder) holder).ll_count.setVisibility(View.GONE);

                    ((ViewHolder) holder).tv_time_title.setText("行程时间");
                    break;
                case Constant.SERVICE_TYPE_CAR:
                case Constant.SERVICE_TYPE_GUIDE:
                    ((ViewHolder) holder).tv_day.setText("x" + data.getTimeDay() + "天");
                    ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * data.getTimeDay());

                    ((ViewHolder) holder).ll_count.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tv_count_title.setText("出行人数");
                    ((ViewHolder) holder).tv_count_content.setText(data.getNumber() + "");

                    ((ViewHolder) holder).tv_time_title.setText("出行日期");
                    break;
                case Constant.SERVICE_TYPE_HOTEL:
                    ((ViewHolder) holder).tv_day.setText("x" + data.getNumber() + "间" + "x" +  data.getTimeDay() + "天");
                    ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * data.getNumber() * data.getTimeDay());

                    ((ViewHolder) holder).ll_count.setVisibility(View.GONE);

                    ((ViewHolder) holder).tv_time_title.setText("入住时间");
                    break;
                case Constant.SERVICE_TYPE_TICKET:
                    ((ViewHolder) holder).tv_day.setText("x" + data.getNumber() + "张");
                    ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * data.getNumber());

                    ((ViewHolder) holder).ll_count.setVisibility(View.GONE);

                    ((ViewHolder) holder).tv_time_title.setText("日期");
                    break;
            }

            if(data.getServeType() == Constant.SERVICE_TYPE_CUSTOM
                    || data.getServeType() == Constant.SERVICE_TYPE_TICKET){
                ((ViewHolder) holder).tv_time_content.setText(data.getOneTime());
            }else {
                ((ViewHolder) holder).tv_time_content.setText(data.getDaysStr());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
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
        LinearLayout ll_count;
        TextView tv_count_title, tv_count_content, tv_time_title, tv_time_content;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price_unit = itemView.findViewById(R.id.tv_price_unit);
            tv_day = itemView.findViewById(R.id.tv_day);
            tv_price_total = itemView.findViewById(R.id.tv_price_total);
            ll_count = itemView.findViewById(R.id.ll_count);
            tv_time_content = itemView.findViewById(R.id.tv_time_content);
            tv_time_title = itemView.findViewById(R.id.tv_time_title);
            tv_count_content = itemView.findViewById(R.id.tv_count_content);
            tv_count_title = itemView.findViewById(R.id.tv_count_title);

        }
    }
}
