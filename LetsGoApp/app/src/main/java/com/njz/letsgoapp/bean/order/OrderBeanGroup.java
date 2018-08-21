package com.njz.letsgoapp.bean.order;

import com.njz.letsgoapp.bean.home.ServiceItem;
import com.njz.letsgoapp.widget.PriceView;

/**
 * Created by LGQ
 * Time: 2018/8/13
 * Function:
 */

public class OrderBeanGroup {

    public static final int LABEL_TAB_TITLE = 1;
    public static final int LABEL_TAB_DEFAULT = 2;
    public static final int LABEL_TAB_FOOT = 3;

    private int labelTab;
    private Suborders suborders;
    private String orderNo;
    private String orderStatus;
    private String orderStartTime;
    private String orderEndTime;
    private double orderTotalPrice;

    public int getLabelTab() {
        return labelTab;
    }

    public void setLabelTab(int labelTab) {
        this.labelTab = labelTab;
    }

    public Suborders getSuborders() {
        return suborders;
    }

    public void setSuborders(Suborders suborders) {
        this.suborders = suborders;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStartTime() {
        return orderStartTime;
    }

    public void setOrderStartTime(String orderStartTime) {
        this.orderStartTime = orderStartTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }
}
