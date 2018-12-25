package com.njz.letsgoapp.adapter.order;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.order.OrderRefundDetailChildModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.server.ServiceDetailActivity;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/16
 * Function:
 */

public class OrderRefundDetailAdapter extends RecyclerView.Adapter<OrderRefundDetailAdapter.BaseViewHolder> {

    private Context context;
    private List<OrderRefundDetailChildModel> datas;

    public OrderRefundDetailAdapter(Context context, List<OrderRefundDetailChildModel> datas) {
        this.context = context;
        this.datas = datas;
    }

    public void setData(List<OrderRefundDetailChildModel> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_order_detail_refund, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof ViewHolder) {
            final int pos = holder.getAdapterPosition();
            final OrderRefundDetailChildModel data = datas.get(pos);
            if (data == null) return;

            GlideUtil.LoadRoundImage(context, data.getTitleImg(), ((ViewHolder) holder).iv_img,5);
            ((ViewHolder) holder).tv_title.setText(data.getTitle());
            ((ViewHolder) holder).tv_server_name.setText(data.getServerName());
            ((ViewHolder) holder).tv_price_total.setText("￥" + data.getOrderPrice());

            ((ViewHolder) holder).ll_count.setVisibility(View.VISIBLE);
            if(data.getServeType() == Constant.SERVER_TYPE_GUIDE_ID){
                ((ViewHolder) holder).ll_count.setVisibility(View.GONE);
            }
            ((ViewHolder) holder).tv_count_content.setText(data.getCountContent());

            ((ViewHolder) holder).tv_time_title.setText(data.getTimeTitle());
            ((ViewHolder) holder).tv_time_content.setText(data.getTravelDate());
            ((ViewHolder) holder).tv_location_content.setText(data.getLocation());


            ((ViewHolder) holder).tv_penalty_content.setText("￥"+data.getDefaultMoney());
            ((ViewHolder) holder).tv_refund_price_content.setText("￥"+data.getRefundMoney());

            ((ViewHolder) holder).ll_travel_going.setVisibility(View.GONE);

            if(data.getChildOrderStatus() == Constant.ORDER_TRAVEL_GOING && data.getServeType() != Constant.SERVER_TYPE_TICKET_ID){
                ((ViewHolder) holder).ll_travel_going.setVisibility(View.VISIBLE);
                ((ViewHolder) holder).tv_travel_day_content.setText(data.getUseDay()+"天");
                ((ViewHolder) holder).tv_not_travel_day_content.setText(data.getUnUseDay()+"天");
                ((ViewHolder) holder).tv_used_price_content.setText("￥" + data.getUseMoney());
            }

            ((ViewHolder) holder).fl_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ServiceDetailActivity.class);
                    intent.putExtra(ServiceDetailActivity.SERVICEID,data.getServeId());
                    intent.putExtra("isHideBottom",true);
                    context.startActivity(intent);
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class BaseViewHolder extends RecyclerView.ViewHolder {
        BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ViewHolder extends BaseViewHolder {

        ImageView iv_img;
        TextView tv_title, tv_server_name, tv_price_total;
        LinearLayout ll_count,ll_travel_going;
        TextView  tv_count_content, tv_time_title, tv_time_content,btn_cancel
                ,tv_travel_day_content,tv_used_price_content,tv_not_travel_day_content,tv_location_content;
        FrameLayout fl_parent;

        TextView tv_penalty_title,tv_penalty_content,tv_refund_price_title,tv_refund_price_content;

        public ViewHolder(View itemView) {
            super(itemView);
            fl_parent = itemView.findViewById(R.id.fl_parent);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            ll_travel_going = itemView.findViewById(R.id.ll_travel_going);
            tv_server_name = itemView.findViewById(R.id.tv_price_unit);
            tv_price_total = itemView.findViewById(R.id.tv_price_total);
            ll_count = itemView.findViewById(R.id.ll_count);
            tv_time_content = itemView.findViewById(R.id.tv_time_content);
            tv_time_title = itemView.findViewById(R.id.tv_time_title);
            tv_count_content = itemView.findViewById(R.id.tv_count_content);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            tv_penalty_title = itemView.findViewById(R.id.tv_penalty_title);
            tv_penalty_content = itemView.findViewById(R.id.tv_penalty_content);
            tv_refund_price_title = itemView.findViewById(R.id.tv_refund_price_title);
            tv_refund_price_content = itemView.findViewById(R.id.tv_refund_price_content);
            tv_travel_day_content = itemView.findViewById(R.id.tv_travel_day_content);
            tv_used_price_content = itemView.findViewById(R.id.tv_used_price_content);
            tv_not_travel_day_content = itemView.findViewById(R.id.tv_not_travel_day_content);
            tv_location_content = itemView.findViewById(R.id.tv_location_content);


        }
    }
}
