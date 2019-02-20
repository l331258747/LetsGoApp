package com.njz.letsgoapp.bean.order;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public class OrderRefundModel {


    /**
     * defaultMoney : 0
     * orderNo : ZJJ201809271515087730
     * specialRequire :
     * orderId : 100
     * refundReason : XXX
     * refundTime : null
     * guideCheckTime : null
     * mobile :
     * orderStatus : 0
     * refundStatus : 1
     * refundMoney : 12.0
     * refundContent : XXXX
     * mainly : 0
     * name :
     * useMoney : 0
     * location : 张家界
     * njzChildOrderToRefundVOS : [{"defaultMoney":0,"titleImg":"11111111111","serveType":26,"unUseDay":0,"orderStatus":0,"refundMoney":0,"personNum":0,"title":"张家界峡谷大酒店","roomNum":2,"travelDate":"","payPrice":0,"price":128,"ticketNum":0,"childOrderId":0,"dayNum":2,"useMoney":0,"value":"ddjd","useDay":0},{"defaultMoney":0,"titleImg":"11111111111","serveType":26,"unUseDay":0,"orderStatus":0,"refundMoney":0,"personNum":0,"title":"张家界天门山大酒店","roomNum":2,"travelDate":"","payPrice":0,"price":128,"ticketNum":0,"childOrderId":0,"dayNum":1,"useMoney":0,"value":"ddjd","useDay":0},{"defaultMoney":0,"titleImg":"11111111111","serveType":24,"unUseDay":0,"orderStatus":0,"refundMoney":0,"personNum":2,"title":"奔驰大队带你飞","roomNum":0,"travelDate":"","payPrice":0,"price":333,"ticketNum":0,"childOrderId":0,"dayNum":3,"useMoney":0,"value":"cdfw","useDay":0}]
     * applyTime : null
     * guideName : sj
     */



    private float defaultMoney;
    private String orderNo;
    private String specialRequire;
    private int orderId;
    private String refundReason;
    private String refundTime;
    private String guideCheckTime;
    private String mobile;
    private int orderStatus;
    private int refundStatus;
    private float refundMoney;
    private String refundContent;
    private int mainly;
    private String name;
    private float useMoney;
    private String location;
    private String applyTime;
    private String guideName;
    private String guideMobile;
    private List<OrderRefundChildModel> njzChildOrderToRefundVOS;
    private int id;
    private int payStatus;
    private int planStatus;
    private float orderPrice;

    public float getOrderPrice() {
        return orderPrice;
    }

    public int getPlanStatus() {
        return planStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public String getGuideMobile() {
        return guideMobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(int defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSpecialRequire() {
        return specialRequire;
    }

    public void setSpecialRequire(String specialRequire) {
        this.specialRequire = specialRequire;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public String getGuideCheckTime() {
        return guideCheckTime;
    }

    public void setGuideCheckTime(String guideCheckTime) {
        this.guideCheckTime = guideCheckTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public float getRefundMoney() {
        return refundMoney;
    }

    public float getMoney(){
        if(payStatus == 4){
            return refundMoney;
        }
        return orderPrice;
    }

    public void setRefundMoney(float refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getRefundContent() {
        return refundContent;
    }

    public void setRefundContent(String refundContent) {
        this.refundContent = refundContent;
    }

    public int getMainly() {
        return mainly;
    }

    public void setMainly(int mainly) {
        this.mainly = mainly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(float useMoney) {
        this.useMoney = useMoney;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public List<OrderRefundChildModel> getNjzChildOrderToRefundVOS() {
        return njzChildOrderToRefundVOS;
    }

    public void setNjzChildOrderToRefundVOS(List<OrderRefundChildModel> njzChildOrderToRefundVOS) {
        this.njzChildOrderToRefundVOS = njzChildOrderToRefundVOS;
    }
}
