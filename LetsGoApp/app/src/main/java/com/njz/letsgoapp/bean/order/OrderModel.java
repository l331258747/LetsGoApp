package com.njz.letsgoapp.bean.order;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/25
 * Function:
 */

public class OrderModel {


    /**
     * orderNo : ZJJ201809251113023327
     * payPrice : 0
     * location : 张家界
     * orderPrice : 1767.0
     * id : 86
     * payStatus : 0
     * guideName : sj
     * njzChildOrderListVOS : [{"roomNum":2,"payPrice":0,"price":128,"ticketNum":0,"dayNum":2,"orderPrice":512,"id":159,"personNum":0,"title":"张家界峡谷大酒店"},{"roomNum":2,"payPrice":0,"price":128,"ticketNum":0,"dayNum":1,"orderPrice":256,"id":160,"personNum":0,"title":"张家界天门山大酒店"},{"roomNum":0,"payPrice":0,"price":333,"ticketNum":0,"dayNum":3,"orderPrice":999,"id":161,"personNum":2,"title":"奔驰大队带你飞"}]
     */

    private String orderNo;
    private float payPrice;
    private int orderStatus;
    private int reviewStatus;
    private String location;
    private float orderPrice;
    private int id;
    private int payStatus;
    private String guideName;
    private String mobile;
    private String name;
    private String guideMobile;
    private List<OrderChildModel> njzChildOrderListVOS;

    public String getGuideMobile() {
        return guideMobile;
    }

    public void setGuideMobile(String guideMobile) {
        this.guideMobile = guideMobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
            case 0:
                return "待付款";
            case 1:
                return "已付款";
            case 2:
                return "已完成";
            case 3:
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

    public List<OrderChildModel> getNjzChildOrderListVOS() {
        return njzChildOrderListVOS;
    }

    public void setNjzChildOrderListVOS(List<OrderChildModel> njzChildOrderListVOS) {
        this.njzChildOrderListVOS = njzChildOrderListVOS;
    }

}
