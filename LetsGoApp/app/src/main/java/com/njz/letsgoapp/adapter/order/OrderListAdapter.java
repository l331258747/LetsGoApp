package com.njz.letsgoapp.adapter.order;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.bean.order.OrderBeanGroup;
import com.njz.letsgoapp.bean.order.OrderChildModel;
import com.njz.letsgoapp.bean.order.OrderModel;
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

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.BaseViewHolder> {


    private static final int ORDER_TYPE_TITLE = 10;
    private static final int ORDER_TYPE_FOOT = 12;

    Context mContext;
    List<OrderModel> datas;

    private List<OrderBeanGroup> orderBeanGroups = new ArrayList<>();

    public OrderListAdapter(Context mContext, List<OrderModel> datas) {
        this.mContext = mContext;
        this.datas = datas;

        setData(datas);
    }

    public void setData(List<OrderModel> datas) {
        orderBeanGroups.clear();
        this.datas = datas;
        setData2(datas);
        notifyDataSetChanged();
    }

    public void addData(List<OrderModel> datas) {
        this.datas.addAll(datas);
        setData(this.datas);
    }

    public List<OrderModel> getData() {
        return datas;
    }

    public OrderModel getItem(int position) {
        return this.datas.get(position);
    }

    public void setData2(List<OrderModel> datas) {
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                OrderBeanGroup serviceInfoGroup = new OrderBeanGroup();
                OrderModel orderModel = datas.get(i);

                serviceInfoGroup.setLabelTab(OrderBeanGroup.LABEL_TAB_TITLE);
                serviceInfoGroup.setOrderNo(orderModel.getOrderNo());
                serviceInfoGroup.setId(orderModel.getId());
                serviceInfoGroup.setGuideName(orderModel.getGuideName());
                serviceInfoGroup.setPayStatus(orderModel.getPayStatus());
                serviceInfoGroup.setOrderStatus(orderModel.getOrderStatus());
                serviceInfoGroup.setReviewStatus(orderModel.getReviewStatus());
                serviceInfoGroup.setIndex(i);
                orderBeanGroups.add(serviceInfoGroup);
                for (int j = 0; j < orderModel.getNjzChildOrderListVOS().size(); j++) {
                    OrderBeanGroup serviceInfoGroup2 = new OrderBeanGroup();
                    OrderChildModel orderChildModel = orderModel.getNjzChildOrderListVOS().get(j);
                    serviceInfoGroup2.setLabelTab(OrderBeanGroup.LABEL_TAB_DEFAULT);
                    serviceInfoGroup2.setOrderChildModel(orderChildModel);
                    serviceInfoGroup2.setIndex(i);
                    orderBeanGroups.add(serviceInfoGroup2);
                }
                OrderBeanGroup serviceInfoGroup3 = new OrderBeanGroup();
                serviceInfoGroup3.setLabelTab(OrderBeanGroup.LABEL_TAB_FOOT);
                serviceInfoGroup3.setPayStatus(orderModel.getPayStatus());
                serviceInfoGroup3.setOrderPrice(orderModel.getOrderPrice());
                serviceInfoGroup3.setOrderStatus(orderModel.getOrderStatus());
                serviceInfoGroup3.setReviewStatus(orderModel.getReviewStatus());
                serviceInfoGroup3.setGuideName(orderModel.getGuideName());
                serviceInfoGroup3.setId(orderModel.getId());
                serviceInfoGroup3.setOrderNo(orderModel.getOrderNo());
                serviceInfoGroup3.setLocation(orderModel.getLocation());
                serviceInfoGroup3.setUserName(orderModel.getName());
                serviceInfoGroup3.setUserMobile(orderModel.getMobile());
                serviceInfoGroup3.setGuideMobile(orderModel.getGuideMobile());
                serviceInfoGroup3.setIndex(i);
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
            final OrderChildModel data = orderBeanGroups.get(pos).getOrderChildModel();
            if (data == null) return;

            GlideUtil.LoadRoundImage(mContext, data.getTitleImg(), ((DefaultHolder) holder).iv_img, 5);

            ((DefaultHolder) holder).tv_title.setText(data.getTitle());


            ((DefaultHolder) holder).btn_cancel.setVisibility(View.VISIBLE);
            switch (data.getPayStatus()) {
                case Constant.ORDER_PAY_WAIT:
                    ((DefaultHolder) holder).btn_cancel.setText("取消");
                    ((DefaultHolder) holder).btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnCancelClickListener != null) {
                                mOnCancelClickListener.onClick(data.getId(), orderBeanGroups.get(pos).getIndex());
                            }
                        }
                    });
                    break;
                case Constant.ORDER_PAY_ALREADY:
                    ((DefaultHolder) holder).btn_cancel.setText("退款");
                    ((DefaultHolder) holder).btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mOnRefundClickListenter != null) {
                                List<Integer> childIds = new ArrayList<Integer>();
                                childIds.add(data.getId());
                                mOnRefundClickListenter.onClick(0, childIds, data.getChildOrderStatus(), orderBeanGroups.get(pos).getIndex());
                            }
                        }
                    });
                    break;
                default:
                    ((DefaultHolder) holder).btn_cancel.setVisibility(View.GONE);
                    break;
            }

            ((DefaultHolder) holder).tv_price.setText("￥" + data.getPrice());

            setNum(data, ((DefaultHolder) holder).tv_num);
            ((DefaultHolder) holder).tv_total_price.setText("￥" + data.getOrderPrice());
        }

        if (holder instanceof TitleHolder) {
            final int pos = holder.getAdapterPosition();
            final OrderBeanGroup data = orderBeanGroups.get(pos);
            if (data == null) return;

            ((TitleHolder) holder).tv_order.setText(data.getOrderNo());
            ((TitleHolder) holder).tv_status.setText(data.getPayStatusStr());
            ((TitleHolder) holder).tv_name.setText(data.getGuideName());

            if (mOnItemClickListener != null) {
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
            final OrderBeanGroup data = orderBeanGroups.get(pos);
            if (data == null) return;

            ((FootHolder) holder).tv_order_price_content.setText("" + data.getOrderPrice());

            ((FootHolder) holder).setbtn();
            switch (data.getPayStatus()) {
                case Constant.ORDER_PAY_WAIT:
                    ((FootHolder) holder).tv_order_price_title.setText("合计");
                    ((FootHolder) holder).btn_cancel_order.setVisibility(View.VISIBLE);
                    ((FootHolder) holder).btn_call_guide.setVisibility(View.VISIBLE);
                    ((FootHolder) holder).btn_pay.setVisibility(View.VISIBLE);
                    break;
                case Constant.ORDER_PAY_ALREADY:
                    ((FootHolder) holder).tv_order_price_title.setText("已付款");
                    switch (data.getOrderStatus()) {
                        case Constant.ORDER_TRAVEL_WAIT:
                        case Constant.ORDER_TRAVEL_NO_GO:
                            ((FootHolder) holder).btn_call_customer.setVisibility(View.VISIBLE);
                            ((FootHolder) holder).btn_call_guide.setVisibility(View.VISIBLE);
                            ((FootHolder) holder).btn_refund.setVisibility(View.VISIBLE);
                            break;
                        case Constant.ORDER_TRAVEL_GOING:
                            ((FootHolder) holder).btn_call_customer.setVisibility(View.VISIBLE);
                            ((FootHolder) holder).btn_call_guide.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case Constant.ORDER_PAY_FINISH:
                    ((FootHolder) holder).tv_order_price_title.setText("已付金额");
                    switch (data.getReviewStatus()) {
                        case Constant.ORDER_EVALUATE_NO:
                            ((FootHolder) holder).btn_call_guide.setVisibility(View.VISIBLE);
                            ((FootHolder) holder).btn_evaluate.setVisibility(View.VISIBLE);
                            break;
                        case Constant.ORDER_EVALUATE_YES:
                            ((FootHolder) holder).btn_delete.setVisibility(View.VISIBLE);
                            ((FootHolder) holder).btn_call_guide.setVisibility(View.VISIBLE);
                            break;
                    }
                    break;
                case Constant.ORDER_PAY_REFUND:

                    break;
            }

            ((FootHolder) holder).btn_evaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderEvaluateActivity.class);
                    intent.putExtra("ORDER_ID", data.getId());
                    intent.putExtra("GUIDE_ID", data.getId());
                    mContext.startActivity(intent);
                }
            });
            ((FootHolder) holder).btn_call_guide.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.getInstance().showGuideMobileDialog(mContext, data.getGuideMobile());
                }
            });
            ((FootHolder) holder).btn_cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderCancelActivity.class);
                    intent.putExtra("ORDER_ID", data.getId());
                    intent.putExtra("name", data.getUserName());
                    intent.putExtra("phone", data.getUserMobile());
                    mContext.startActivity(intent);
                }
            });
            ((FootHolder) holder).btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PayModel payModel = new PayModel();
                    payModel.setTotalAmount(data.getOrderPrice() + "");
                    payModel.setSubject(data.getLocation() + data.getGuideName() + "导游为您服务！");
                    payModel.setOutTradeNo(data.getOrderNo());
                    payModel.setLastPayTime("2018-01-01 12:00");
                    PayActivity.startActivity(mContext, payModel);//TODO 订单上传成功，返回单号
                }
            });
            ((FootHolder) holder).btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showShortToast(mContext, "删除");
                }
            });
            ((FootHolder) holder).btn_call_customer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtil.getInstance().showCustomerMobileDialog(mContext);
                }
            });
            ((FootHolder) holder).btn_refund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OrderRefundActivity.class);
                    intent.putExtra("id", data.getId());
                    List<Integer> childIds = new ArrayList<Integer>();
                    for (int i = 0; i < datas.get(data.getIndex()).getNjzChildOrderListVOS().size(); i++) {
                        childIds.add(datas.get(data.getIndex()).getNjzChildOrderListVOS().get(i).getId());
                    }
                    intent.putIntegerArrayListExtra("childIds", (ArrayList<Integer>) childIds);
                    intent.putExtra("name", data.getUserName());
                    intent.putExtra("phone", data.getUserMobile());
                    intent.putExtra("status", data.getOrderStatus());
                    mContext.startActivity(intent);
                }
            });

        }
    }

    //设置数量计算方式
    public void setNum(OrderChildModel data, TextView tv) {
        switch (data.getValue()) {
            case Constant.SERVICE_TYPE_SHORT_CUSTOM:
                tv.setText("x" + data.getPersonNum() + "人");
                break;
            case Constant.SERVICE_TYPE_SHORT_GUIDE:
            case Constant.SERVICE_TYPE_SHORT_CAR:
                tv.setText("x" + data.getDayNum() + "天");
                break;
            case Constant.SERVICE_TYPE_SHORT_HOTEL:
                tv.setText("x" + data.getDayNum() + "天x" + data.getRoomNum() + "间");
                break;
            case Constant.SERVICE_TYPE_SHORT_TICKET:
                tv.setText("x" + data.getTicketNum() + "张");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderBeanGroups.size();
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class TitleHolder extends OrderListAdapter.BaseViewHolder implements View.OnClickListener {
        TextView tv_order, tv_status, tv_name;
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

    public class DefaultHolder extends OrderListAdapter.BaseViewHolder {
        ImageView iv_img;
        TextView tv_title, btn_cancel, tv_price, tv_num, tv_total_price;

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


    public class FootHolder extends OrderListAdapter.BaseViewHolder {
        TextView tv_order_price_content, tv_order_price_title;
        TextView btn_call_guide, btn_cancel_order, btn_pay, btn_evaluate, btn_delete, btn_call_customer, btn_refund;

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
        }

        public void setbtn() {
            btn_call_guide.setVisibility(View.GONE);
            btn_cancel_order.setVisibility(View.GONE);
            btn_pay.setVisibility(View.GONE);
            btn_evaluate.setVisibility(View.GONE);
            btn_delete.setVisibility(View.GONE);
            btn_call_customer.setVisibility(View.GONE);
            btn_refund.setVisibility(View.GONE);
        }
    }

    //---------事件 start---------
    OnItemClickListener mOnItemClickListener;
    OnCancelClickListener mOnCancelClickListener;
    OnRefundClickListenter mOnRefundClickListenter;

    public interface OnItemClickListener {
        void onClick(int orderId);
    }

    public interface OnCancelClickListener {
        void onClick(int orderId, int index);
    }

    public interface OnRefundClickListenter {
        void onClick(int id, List<Integer> childIds, int status, int index);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnCancelClickListener(OnCancelClickListener onCancelClickListener) {
        this.mOnCancelClickListener = onCancelClickListener;
    }

    public void setOnRefundClickListener(OnRefundClickListenter onRefundClickListener) {
        this.mOnRefundClickListenter = onRefundClickListener;
    }

    //---------事件 end---------

}
