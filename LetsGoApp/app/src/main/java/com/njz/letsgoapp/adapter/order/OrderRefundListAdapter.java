package com.njz.letsgoapp.adapter.order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.order.OrderBeanGroup;
import com.njz.letsgoapp.bean.order.OrderRefundBeanGroup;
import com.njz.letsgoapp.bean.order.OrderRefundChildModel;
import com.njz.letsgoapp.bean.order.OrderRefundModel;
import com.njz.letsgoapp.bean.order.PayModel;
import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.dialog.DialogUtil;
import com.njz.letsgoapp.util.ToastUtil;
import com.njz.letsgoapp.util.glide.GlideUtil;
import com.njz.letsgoapp.view.order.OrderCancelActivity;
import com.njz.letsgoapp.view.order.OrderEvaluateActivity;
import com.njz.letsgoapp.view.order.OrderRefundActivity;
import com.njz.letsgoapp.view.pay.PayActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/8/21
 * Function:
 */

public class OrderRefundListAdapter extends RecyclerView.Adapter<OrderRefundListAdapter.BaseViewHolder> {


    private static final int ORDER_TYPE_TITLE = 10;
    private static final int ORDER_TYPE_FOOT = 12;

    Context mContext;
    List<OrderRefundModel> datas;

    private List<OrderRefundBeanGroup> orderBeanGroups = new ArrayList<>();

    public OrderRefundListAdapter(Context mContext, List<OrderRefundModel> datas) {
        this.mContext = mContext;
        this.datas = datas;

        setData(datas);
    }

    public void setData(List<OrderRefundModel> datas) {
        orderBeanGroups.clear();
        this.datas = datas;
        setData2(datas);
        notifyDataSetChanged();
    }

    public void addData(List<OrderRefundModel> datas){
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public List<OrderRefundModel> getData(){
        return datas;
    }

    public OrderRefundModel getItem(int position){
        return this.datas.get(position);
    }

    public void setData2(List<OrderRefundModel> datas) {
        if (datas != null) {
            for (int i = 0;i<datas.size();i++){
                OrderRefundBeanGroup serviceInfoGroup = new OrderRefundBeanGroup();
                OrderRefundModel orderModel = datas.get(i);

                serviceInfoGroup.setLabelTab(OrderRefundBeanGroup.LABEL_TAB_TITLE);
                serviceInfoGroup.setOrderNo(orderModel.getOrderNo());
                serviceInfoGroup.setId(orderModel.getId());
                serviceInfoGroup.setGuideName(orderModel.getGuideName());
                serviceInfoGroup.setPayStatus(Constant.ORDER_PAY_REFUND);
                serviceInfoGroup.setOrderStatus(orderModel.getOrderStatus());
                serviceInfoGroup.setReviewStatus(Constant.ORDER_EVALUATE_NO);
                serviceInfoGroup.setRefundStatus(orderModel.getRefundStatus());
                orderBeanGroups.add(serviceInfoGroup);
                for (int j = 0; j<orderModel.getNjzChildOrderToRefundVOS().size();j++){
                    OrderRefundBeanGroup serviceInfoGroup2 = new OrderRefundBeanGroup();
                    OrderRefundChildModel orderChildModel = orderModel.getNjzChildOrderToRefundVOS().get(j);
                    serviceInfoGroup2.setLabelTab(OrderRefundBeanGroup.LABEL_TAB_DEFAULT);
                    serviceInfoGroup2.setOrderChildModel(orderChildModel);
                    orderBeanGroups.add(serviceInfoGroup2);
                }
                OrderRefundBeanGroup serviceInfoGroup3 = new OrderRefundBeanGroup();
                serviceInfoGroup3.setLabelTab(OrderRefundBeanGroup.LABEL_TAB_FOOT);
                serviceInfoGroup3.setPayStatus(Constant.ORDER_PAY_REFUND);
                serviceInfoGroup3.setOrderPrice(orderModel.getDefaultMoney());
                serviceInfoGroup3.setOrderStatus(orderModel.getOrderStatus());
                serviceInfoGroup3.setReviewStatus(Constant.ORDER_EVALUATE_NO);
                serviceInfoGroup3.setGuideName(orderModel.getGuideName());
                serviceInfoGroup3.setId(orderModel.getId());
                serviceInfoGroup3.setRefundStatus(orderModel.getRefundStatus());
                serviceInfoGroup3.setOrderNo(orderModel.getOrderNo());
                serviceInfoGroup3.setLocation(orderModel.getLocation());
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
        if (orderBeanGroups.get(position).getLabelTab() == OrderRefundBeanGroup.LABEL_TAB_TITLE)
            return ORDER_TYPE_TITLE;
        if (orderBeanGroups.get(position).getLabelTab() == OrderRefundBeanGroup.LABEL_TAB_FOOT)
            return ORDER_TYPE_FOOT;
        return super.getItemViewType(position);

    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (holder == null) return;
        if (holder instanceof DefaultHolder) {
            final int pos = holder.getAdapterPosition();
            final OrderRefundChildModel data = orderBeanGroups.get(pos).getOrderChildModel();
            if (data == null) return;

            GlideUtil.LoadRoundImage(mContext,data.getTitleImg(),((DefaultHolder) holder).iv_img,5);

            ((DefaultHolder) holder).tv_title.setText(data.getTitle());


            ((DefaultHolder) holder).btn_cancel.setVisibility(View.GONE);

            ((DefaultHolder) holder).tv_price.setText("￥" + data.getPrice());

            setNum(data,((DefaultHolder) holder).tv_num);
            ((DefaultHolder) holder).tv_total_price.setText("￥" + data.getDefaultMoney());
        }

        if (holder instanceof TitleHolder) {
            final int pos = holder.getAdapterPosition();
            final OrderRefundBeanGroup data = orderBeanGroups.get(pos);
            if (data == null) return;

            ((TitleHolder) holder).tv_order.setText(data.getOrderNo());
            ((TitleHolder) holder).tv_status.setText(data.getPayStatusStr());
            ((TitleHolder) holder).tv_name.setText(data.getGuideName());

            if(mOnItemClickListener != null){
                ((TitleHolder) holder).rl_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onClick(data.getId());
                    }
                });
            }
        }

        if (holder instanceof FootHolder) {
            final int pos = holder.getAdapterPosition();
            final OrderRefundBeanGroup data = orderBeanGroups.get(pos);
            if (data == null) return;

            ((FootHolder) holder).setbtn();
            switch (data.getPayStatus()){
                case Constant.ORDER_PAY_REFUND:
                    switch (data.getRefundStatus()){
                        case Constant.ORDER_REFUND_WAIT:
                        case Constant.ORDER_REFUND_PROCESS:
                            ((FootHolder) holder).btn_call_guide.setVisibility(View.VISIBLE);
                            ((FootHolder) holder).btn_call_customer.setVisibility(View.VISIBLE);
                            break;
                        case Constant.ORDER_REFUND_FINISH:
                            ((FootHolder) holder).btn_delete.setVisibility(View.VISIBLE);
                            ((FootHolder) holder).rl_price.setVisibility(View.VISIBLE);
                            ((FootHolder) holder).tv_order_price_title.setText("金额");
                            ((FootHolder) holder).tv_order_price_content.setText("" + data.getOrderPrice());
                            break;
                    }
                    break;
            }
            ((FootHolder) holder).btn_call_guide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.getInstance().getDefaultDialog(mContext, "提示", "13211111111", "呼叫", new DialogUtil.DialogCallBack() {
                        @Override
                        public void exectEvent(DialogInterface alterDialog) {
                            Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13211111111"));
                            mContext.startActivity(dialIntent);
                            alterDialog.dismiss();
                        }
                    }).show();
                }
            });
            ((FootHolder) holder).btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShortToast(mContext,"删除");
                }
            });
            ((FootHolder) holder).btn_call_customer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.getInstance().getDefaultDialog(mContext, "提示", "13211111111", "呼叫", new DialogUtil.DialogCallBack() {
                        @Override
                        public void exectEvent(DialogInterface alterDialog) {
                            Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13211111111"));
                            mContext.startActivity(dialIntent);
                            alterDialog.dismiss();
                        }
                    }).show();
                }
            });
        }
    }

    //设置数量计算方式
    public void setNum(OrderRefundChildModel data, TextView tv){
        switch (data.getValue()){
            case Constant.SERVICE_TYPE_SHORT_CUSTOM:
                tv.setText("x"+data.getPersonNum()+"人");
                break;
            case Constant.SERVICE_TYPE_SHORT_GUIDE:
            case Constant.SERVICE_TYPE_SHORT_CAR:
                tv.setText("x"+data.getDayNum()+"天");
                break;
            case Constant.SERVICE_TYPE_SHORT_HOTEL:
                tv.setText("x"+data.getDayNum()+"天x" + data.getRoomNum()+"间");
                break;
            case Constant.SERVICE_TYPE_SHORT_TICKET:
                tv.setText("x"+data.getTicketNum()+"张");
                break;
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

    public class TitleHolder extends OrderRefundListAdapter.BaseViewHolder implements View.OnClickListener {
        TextView tv_order,tv_status,tv_name;
        RelativeLayout rl_status;

        TitleHolder(View itemView) {
            super(itemView);
            tv_order = itemView.findViewById(R.id.tv_order);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_status = itemView.findViewById(R.id.tv_status);
            rl_status = itemView.findViewById(R.id.rl_status);

            rl_status.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public class DefaultHolder extends OrderRefundListAdapter.BaseViewHolder {
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


    public class FootHolder extends OrderRefundListAdapter.BaseViewHolder {
        TextView tv_order_price_content,tv_order_price_title;
        TextView btn_call_guide,btn_cancel_order,btn_pay,btn_evaluate,btn_delete,btn_call_customer,btn_refund;
        RelativeLayout rl_price;

        FootHolder(View itemView) {
            super(itemView);
            tv_order_price_content = itemView.findViewById(R.id.tv_order_price_content);
            tv_order_price_title = itemView.findViewById(R.id.tv_order_price_title);

            btn_call_guide = itemView.findViewById(R.id.btn_call_guide);
            btn_cancel_order = itemView.findViewById(R.id.btn_cancel_order);
            btn_pay = itemView.findViewById(R.id.btn_pay);
            btn_evaluate = itemView.findViewById(R.id.btn_evaluate);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_call_customer = itemView.findViewById(R.id.btn_call_customer);
            btn_refund = itemView.findViewById(R.id.btn_refund);
            rl_price = itemView.findViewById(R.id.rl_price);
        }

        public void setbtn(){
            btn_call_guide.setVisibility(View.GONE);
            btn_cancel_order.setVisibility(View.GONE);
            btn_pay.setVisibility(View.GONE);
            btn_evaluate.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
            btn_call_customer.setVisibility(View.GONE);
            btn_refund.setVisibility(View.GONE);
            rl_price.setVisibility(View.GONE);
        }
    }

    //---------事件 start---------
    OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(int orderId);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    //---------事件 end---------

}
