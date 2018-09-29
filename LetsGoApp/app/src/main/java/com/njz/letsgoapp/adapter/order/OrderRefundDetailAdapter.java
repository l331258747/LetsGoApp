package com.njz.letsgoapp.adapter.order;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.order.OrderDetailChildModel;
import com.njz.letsgoapp.bean.order.OrderRefundDetailChildModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.glide.GlideUtil;

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

            GlideUtil.LoadImage(context, data.getTitleImg(), ((ViewHolder) holder).iv_img);
            ((ViewHolder) holder).tv_title.setText(data.getTitle());
            ((ViewHolder) holder).tv_price_unit.setText("￥" + data.getPrice());

            switch (data.getValue()) {
                case Constant.SERVICE_TYPE_SHORT_CUSTOM:
                    ((ViewHolder) holder).tv_day.setText("x" + data.getPersonNum() + "人");
                    ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * data.getPersonNum());

                    ((ViewHolder) holder).ll_count.setVisibility(View.GONE);

                    ((ViewHolder) holder).tv_time_title.setText("行程时间");
                    break;
                case Constant.SERVICE_TYPE_SHORT_CAR:
                case Constant.SERVICE_TYPE_SHORT_GUIDE:
                    ((ViewHolder) holder).tv_day.setText("x" + data.getDayNum() + "天");
                    ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * data.getDayNum());

                    ((ViewHolder) holder).ll_count.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tv_count_title.setText("出行人数");
                    ((ViewHolder) holder).tv_count_content.setText(data.getPersonNum() + "");

                    ((ViewHolder) holder).tv_time_title.setText("出行日期");
                    break;
                case Constant.SERVICE_TYPE_SHORT_HOTEL:
                    ((ViewHolder) holder).tv_day.setText("x" + data.getRoomNum() + "间" + "x" +  data.getDayNum() + "天");
                    ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * data.getPersonNum() * data.getDayNum());

                    ((ViewHolder) holder).ll_count.setVisibility(View.GONE);

                    ((ViewHolder) holder).tv_time_title.setText("入住时间");
                    break;
                case Constant.SERVICE_TYPE_SHORT_TICKET:
                    ((ViewHolder) holder).tv_day.setText("x" + data.getTicketNum() + "张");
                    ((ViewHolder) holder).tv_price_total.setText("￥" + data.getPrice() * data.getPersonNum());

                    ((ViewHolder) holder).ll_count.setVisibility(View.GONE);

                    ((ViewHolder) holder).tv_time_title.setText("日期");
                    break;
            }

            ((ViewHolder) holder).tv_time_content.setText(data.getTravelDate());
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
        TextView tv_title, tv_price_unit, tv_day, tv_price_total;
        LinearLayout ll_count;
        TextView tv_count_title, tv_count_content, tv_time_title, tv_time_content,btn_cancel;

        TextView tv_penalty_title,tv_penalty_content,tv_refund_price_title,tv_refund_price_content;

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
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            tv_penalty_title = itemView.findViewById(R.id.tv_penalty_title);
            tv_penalty_content = itemView.findViewById(R.id.tv_penalty_content);
            tv_refund_price_title = itemView.findViewById(R.id.tv_refund_price_title);
            tv_refund_price_content = itemView.findViewById(R.id.tv_refund_price_content);


        }
    }
}
