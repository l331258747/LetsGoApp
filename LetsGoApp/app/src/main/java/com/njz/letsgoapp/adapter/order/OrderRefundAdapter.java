package com.njz.letsgoapp.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;

/**
 * Created by LGQ
 * Time: 2018/9/26
 * Function:
 */

public class OrderRefundAdapter extends RecyclerView.Adapter<OrderRefundAdapter.ViewHolder> {

    Context context;

    public OrderRefundAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_refund, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) return;
        final int pos = holder.getAdapterPosition();



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_img;
        TextView tv_title,tv_one_price,tv_num,tv_total_price,tv_travel_day,tv_travel_price;
        TextView tv_not_travel_day,tv_not_travel_price,tv_refund_rule,tv_refund_price;
        RelativeLayout rl_travel_day,rl_travel_price,rl_not_travel_day,rl_not_travel_price,rl_refund_price;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_one_price = itemView.findViewById(R.id.tv_one_price);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_total_price = itemView.findViewById(R.id.tv_total_price);
            tv_travel_day = itemView.findViewById(R.id.tv_travel_day);
            tv_travel_price = itemView.findViewById(R.id.tv_travel_price);
            tv_not_travel_day = itemView.findViewById(R.id.tv_not_travel_day);
            tv_not_travel_price = itemView.findViewById(R.id.tv_not_travel_price);
            tv_refund_rule = itemView.findViewById(R.id.tv_refund_rule);
            tv_refund_price = itemView.findViewById(R.id.tv_refund_price);
            rl_travel_day = itemView.findViewById(R.id.rl_travel_day);

            rl_travel_price = itemView.findViewById(R.id.rl_travel_price);
            rl_not_travel_day = itemView.findViewById(R.id.rl_not_travel_day);
            rl_not_travel_price = itemView.findViewById(R.id.rl_not_travel_price);
            rl_refund_price = itemView.findViewById(R.id.rl_refund_price);

        }
    }
}
