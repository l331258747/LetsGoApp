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
import com.njz.letsgoapp.bean.order.OrderBean;
import com.njz.letsgoapp.bean.order.OrderBeanGroup;
import com.njz.letsgoapp.bean.order.Suborders;
import com.njz.letsgoapp.util.glide.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/21
 * Function:
 */

public class OrderWaitAdapter extends RecyclerView.Adapter<OrderWaitAdapter.BaseViewHolder> {


    private static final int ORDER_TYPE_TITLE = 10;
    private static final int ORDER_TYPE_FOOT = 12;

    Context mContext;
    List<OrderBean> datas;

    private List<OrderBeanGroup> orderBeanGroups = new ArrayList<>();

    public OrderWaitAdapter(Context mContext, List<OrderBean> datas) {
        this.mContext = mContext;
        this.datas = datas;

        setData(datas);
    }

    public void setData(List<OrderBean> datas) {
        orderBeanGroups.clear();
        setData2(datas);
        notifyDataSetChanged();
    }

    public void setData2(List<OrderBean> datas) {
        if (datas != null) {
            for (int i = 0;i<datas.size();i++){
                OrderBeanGroup serviceInfoGroup = new OrderBeanGroup();
                OrderBean orderBean = datas.get(i);

                serviceInfoGroup.setLabelTab(OrderBeanGroup.LABEL_TAB_TITLE);
                serviceInfoGroup.setOrderNo(orderBean.getOrderNo());
                serviceInfoGroup.setOrderStatus(orderBean.getOrderStatus());
                orderBeanGroups.add(serviceInfoGroup);
                for (int j = 0; j<orderBean.getSuborderses().size();j++){
                    OrderBeanGroup serviceInfoGroup2 = new OrderBeanGroup();
                    Suborders suborders = orderBean.getSuborderses().get(j);
                    serviceInfoGroup2.setLabelTab(OrderBeanGroup.LABEL_TAB_DEFAULT);
                    serviceInfoGroup2.setSuborders(suborders);
                    orderBeanGroups.add(serviceInfoGroup2);
                }
                OrderBeanGroup serviceInfoGroup3 = new OrderBeanGroup();
                serviceInfoGroup3.setLabelTab(OrderBeanGroup.LABEL_TAB_FOOT);
                serviceInfoGroup3.setOrderStartTime(orderBean.getOrderStartTime());
                serviceInfoGroup3.setOrderEndTime(orderBean.getOrderEndTime());
                serviceInfoGroup3.setOrderTotalPrice(orderBean.getOrderTotalPrice());
                orderBeanGroups.add(serviceInfoGroup3);
            }
        }
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case ORDER_TYPE_TITLE:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_order_title, parent, false);
                return new TitleHolder(view);
            case ORDER_TYPE_FOOT:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_order_foot, parent, false);
                return new FootHolder(view);
            default:
                view = LayoutInflater.from(mContext).inflate(R.layout.item_order_defualt, parent, false);
                return new DefaultHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (orderBeanGroups.get(position).getLabelTab() == OrderBeanGroup.LABEL_TAB_TITLE)
            return ORDER_TYPE_TITLE;
        if (orderBeanGroups.get(position).getLabelTab() == OrderBeanGroup.LABEL_TAB_FOOT)
            return ORDER_TYPE_FOOT;
        return super.getItemViewType(position);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof DefaultHolder) {
            final int pos = holder.getAdapterPosition();
            final Suborders data = orderBeanGroups.get(pos).getSuborders();
            if (data == null) return;

            GlideUtil.LoadImage(mContext,data.getImgUrl(),((DefaultHolder) holder).iv_img);

            ((DefaultHolder) holder).tv_title.setText(data.getTitle());
            ((DefaultHolder) holder).btn_cancel.setText("取消");
            ((DefaultHolder) holder).tv_price.setText("￥" + data.getPrice());
            ((DefaultHolder) holder).tv_num.setText("x"+data.getNum()+"天");
            ((DefaultHolder) holder).tv_total_price.setText("￥" + data.getTotalPrice());

        }

        if (holder instanceof TitleHolder) {
            final int pos = holder.getAdapterPosition();
            final OrderBeanGroup data = orderBeanGroups.get(pos);
            if (data == null) return;

            ((TitleHolder) holder).tv_title.setText(data.getOrderNo());
            ((TitleHolder) holder).tv_status.setText(data.getOrderStatus());

            if(mOnItemClickListener != null){
                ((TitleHolder) holder).rl_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(data.getOrderNo());
                    }
                });
            }
        }

        if (holder instanceof FootHolder) {
            final int pos = holder.getAdapterPosition();
            final OrderBeanGroup data = orderBeanGroups.get(pos);
            if (data == null) return;

            ((FootHolder) holder).tv_start_time.setText(data.getOrderStartTime());
            ((FootHolder) holder).tv_end_time.setText(data.getOrderEndTime());
            ((FootHolder) holder).tv_price.setText("" + data.getOrderTotalPrice());
            ((FootHolder) holder).btn_1.setText("联系导游");
            ((FootHolder) holder).btn_2.setText("取消订单");
            ((FootHolder) holder).btn_3.setText("付款");
        }
    }

    @Override
    public int getItemCount() {
        return orderBeanGroups.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder{

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TitleHolder extends OrderWaitAdapter.BaseViewHolder implements View.OnClickListener {
        TextView tv_title,tv_status;
        RelativeLayout rl_status;

        TitleHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_status = itemView.findViewById(R.id.tv_status);
            rl_status = itemView.findViewById(R.id.rl_status);

            rl_status.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public class DefaultHolder extends OrderWaitAdapter.BaseViewHolder {
        ImageView iv_img;
        TextView tv_title,btn_cancel,tv_price,tv_num,tv_total_price;

        DefaultHolder(View itemView) {
            super(itemView);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_title = itemView.findViewById(R.id.tv_title);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            tv_price = itemView.findViewById(R.id.tv_comment);
            tv_num = itemView.findViewById(R.id.tv_num);
            tv_total_price = itemView.findViewById(R.id.tv_total_price);
        }
    }


    public class FootHolder extends OrderWaitAdapter.BaseViewHolder {
        TextView tv_start_time,tv_end_time,tv_price;
        TextView btn_1,btn_2,btn_3;

        FootHolder(View itemView) {
            super(itemView);
            tv_start_time = itemView.findViewById(R.id.tv_start_time);
            tv_end_time = itemView.findViewById(R.id.tv_end_time);
            tv_price = itemView.findViewById(R.id.tv_comment);
            btn_1 = itemView.findViewById(R.id.btn_1);
            btn_2 = itemView.findViewById(R.id.btn_2);
            btn_3 = itemView.findViewById(R.id.btn_3);
        }
    }

    //---------事件 start---------
    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(String orderNo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //---------事件 end---------

}
