package com.njz.letsgoapp.bean.order;

import com.njz.letsgoapp.constant.Constant;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public class OrderRefundBeanGroup {

    public static final int LABEL_TAB_TITLE = 1;
    public static final int LABEL_TAB_DEFAULT = 2;
    public static final int LABEL_TAB_FOOT = 3;

    private int labelTab;

    private String orderNo;
    private float payPrice;
    private int orderStatus;
    private String location;
    private float orderPrice;
    private float refundMoney;
    private int reviewStatus;
    private int refundStatus;
    private int id;
    private int payStatus;
    private String guideName;
    private OrderRefundChildModel orderChildModel;

    public float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getLabelTab() {
        return labelTab;
    }

    public void setLabelTab(int labelTab) {
        this.labelTab = labelTab;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public float getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(float payPrice) {
        this.payPrice = payPrice;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public String getPayStatusStr(){
        switch (payStatus){
            case Constant.ORDER_PAY_WAIT:
                return "待付款";
            case Constant.ORDER_PAY_ALREADY:
                switch (orderStatus){
                    case Constant.ORDER_TRAVEL_WAIT:
                        return "导游待确认";
                    case Constant.ORDER_TRAVEL_NO_GO:
                        return "未出行";
                    case Constant.ORDER_TRAVEL_GOING:
                        return "行程中";
                    case Constant.ORDER_TRAVEL_FINISH:
                        return "行程结束";
                    case Constant.ORDER_TRAVEL_REFUSE:
                        return "导游拒绝";
                    default:
                        return "";
                }
            case Constant.ORDER_PAY_FINISH:
                return "已完成";
            case Constant.ORDER_PAY_REFUND:
                switch (refundStatus){
                    case Constant.ORDER_REFUND_WAIT:
                        return "导游待审核";
                    case Constant.ORDER_REFUND_PROCESS:
                        return "退款中";
                    case Constant.ORDER_REFUND_FINISH:
                        return "已退款";
                }
                return "退款";
        }
        return "";
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public OrderRefundChildModel getOrderChildModel() {
        return orderChildModel;
    }

    public void setOrderChildModel(OrderRefundChildModel orderChildModel) {
        this.orderChildModel = orderChildModel;
    }
}
