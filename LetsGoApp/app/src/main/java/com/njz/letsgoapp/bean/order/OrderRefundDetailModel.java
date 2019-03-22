package com.njz.letsgoapp.bean.order;

import com.njz.letsgoapp.constant.Constant;
import com.njz.letsgoapp.util.DecimalUtil;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public class OrderRefundDetailModel {


    /**
     * defaultMoney : 18.0
     * specialRequire : xxxxxx
     * coupon : 0
     * orderId : 0
     * refundReason : XXX
     * refundTime : null
     * guideCheckTime : null
     * mobile : 13977777777
     * refundStatus : 1
     * refundMoney : 12.0
     * refundContent : XXXX
     * mainly : 1
     * name : 李小宇
     * childOrderId : 0
     * useMoney : 0
     * location : 张家界
     * id : 8
     * njzRefundDetailsChildVOS : [{"defaultMoney":0,"titleImg":"11111111111","childOrderStatus":0,"orderId":100,"serveType":26,"unUseDay":0,"refundMoney":0,"personNum":0,"title":"张家界峡谷大酒店","roomNum":2,"travelDate":"2018-10-21,2018-10-22","payPrice":0,"price":128,"ticketNum":0,"dayNum":2,"useMoney":0,"orderPrice":512,"id":186,"payStatus":1,"value":"ddjd","useDay":0},{"defaultMoney":0,"titleImg":"11111111111","childOrderStatus":0,"orderId":100,"serveType":26,"unUseDay":0,"refundMoney":0,"personNum":0,"title":"张家界天门山大酒店","roomNum":2,"travelDate":"2018-10-23","payPrice":0,"price":128,"ticketNum":0,"dayNum":1,"useMoney":0,"orderPrice":256,"id":187,"payStatus":1,"value":"ddjd","useDay":0},{"defaultMoney":0,"titleImg":"11111111111","childOrderStatus":0,"orderId":100,"serveType":24,"unUseDay":0,"refundMoney":0,"personNum":2,"title":"奔驰大队带你飞","roomNum":0,"travelDate":"2018-10-21,2018-10-22,2018-10-23","payPrice":0,"price":333,"ticketNum":0,"dayNum":3,"useMoney":0,"orderPrice":999,"id":188,"payStatus":1,"value":"cdfw","useDay":0}]
     * applyTime : null
     * guideName : sj
     */

    private String orderNo;
    private float defaultMoney;
    private String specialRequire;
    private int coupon;
    private int orderId;
    private String refundReason;
    private String refundTime;
    private String guideCheckTime;
    private String mobile;
    private int refundStatus;
    private float refundMoney;
    private String refundContent;
    private int mainly;
    private String name;
    private int childOrderId;
    private float useMoney;
    private String location;
    private int id;
    private String applyTime;
    private String guideName;
    private int personNum;
    private String guideMobile;
    private List<OrderRefundDetailChildModel> njzRefundDetailsChildVOS;
    private int children;
    private int adult;
    private int guideId;
    private String createTime;
    private String guideSureTime;
    private String planDesignTime;
    private float typeMoney;
    private float orderPrice;
    private int planStatus;

    public int getPlanStatus() {
        return planStatus;
    }

    public String getOrderPrice() {
        if((planStatus == Constant.ORDER_PLAN_GUIDE_WAIT || planStatus == Constant.ORDER_PLAN_PLANING) && orderPrice == 0){
            return ("报价待确定");
        }
        return "￥"+orderPrice;
    }

    public String getPlanDesignTime() {
        return planDesignTime;
    }

    public String getGuideSureTime() {
        return guideSureTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public float getCouponPrice(){
        float couponPrice = 0;
        if(njzRefundDetailsChildVOS == null)
            return couponPrice;

        for (int i =0;i<njzRefundDetailsChildVOS.size();i++){
            couponPrice = DecimalUtil.add(couponPrice,njzRefundDetailsChildVOS.get(i).getCouponPrice());
        }
        return couponPrice;
    }

    public int getGuideId() {
        return guideId;
    }

    public boolean isCustom() {
        if(njzRefundDetailsChildVOS !=null && njzRefundDetailsChildVOS.size()==1 && njzRefundDetailsChildVOS.get(0).getServeType() == Constant.SERVER_TYPE_CUSTOM_ID){
            return true;
        }
        return false;
    }

    public String getPersonNum() {
        if(isCustom() && (adult>0 || children > 0)){
            return adult+"成人"+children+"儿童";
        }
        return personNum + "";
    }

    public String getGuideMobile() {
        return guideMobile;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public float getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(float defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public String getSpecialRequire() {
        return specialRequire;
    }

    public void setSpecialRequire(String specialRequire) {
        this.specialRequire = specialRequire;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
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

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public float getRefundMoney() {
        return refundMoney;
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

    public int getChildOrderId() {
        return childOrderId;
    }

    public void setChildOrderId(int childOrderId) {
        this.childOrderId = childOrderId;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPayStatusStr() {
        switch (refundStatus) {
            case Constant.ORDER_REFUND_WAIT:
                return "导游待审核";
            case Constant.ORDER_REFUND_PROCESS:
                return "退款中";
            case Constant.ORDER_REFUND_FINISH:
                return "已退款";
            case Constant.ORDER_REFUND_CANCEL:
            case Constant.ORDER_REFUND_PLAN_REFUSE:
                return "已取消";
        }
        return "退款";
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public List<OrderRefundDetailChildModel> getNjzRefundDetailsChildVOS() {
        return njzRefundDetailsChildVOS;
    }

    public void setNjzRefundDetailsChildVOS(List<OrderRefundDetailChildModel> njzRefundDetailsChildVOS) {
        this.njzRefundDetailsChildVOS = njzRefundDetailsChildVOS;
    }
}
