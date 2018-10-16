package com.njz.letsgoapp.bean.order;

import android.text.TextUtils;

import com.njz.letsgoapp.constant.Constant;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/28
 * Function:
 */

public class OrderDetailModel {


    /**
     * guideSureTime : null
     * lastPayTime : 2018-09-26 14:55:06
     * orderNo : CS201809251455064533
     * specialRequire :
     * balanceStatus : 0
     * payTime : null
     * mobile : 13222222222
     * orderStatus : 1
     * payType : AliPay
     * createTime : 2018-09-25 14:55:06
     * njzChildOrderVOS : [{"defaultMoney":0,"titleImg":"1231","childOrderStatus":0,"orderId":94,"serveType":0,"unUseDay":0,"refundMoney":0,"personNum":0,"title":"长沙美女带你游","roomNum":1,"travelDate":"2018-9-28,2018-9-29","payPrice":224,"price":112,"ticketNum":0,"dayNum":2,"orderPrice":224,"id":174,"payStatus":0,"value":"xdpy","useDay":0}]
     * payPrice : 0.01
     * name :
     * location : 长沙
     * orderPrice : 224.0
     * reviewStatus : 0
     * endTime : null
     * id : 94
     * payStatus : 1
     * guideName : lm
     */

    private int guideId;
    private String guideSureTime;
    private String lastPayTime;
    private String orderNo;
    private String specialRequire;
    private int balanceStatus;
    private String payTime;
    private String mobile;
    private int orderStatus;
    private String payType;
    private String createTime;
    private float payPrice;
    private String name;
    private String location;
    private float orderPrice;
    private int reviewStatus;
    private String endTime;
    private int id;
    private int payStatus;
    private String guideName;
    private String guideMobile;
    private String startData;
    private String endData;
    private List<OrderDetailChildModel> njzChildOrderVOS;
    private int payingStatus;

    public int getPayingStatus() {
        return payingStatus;
    }

    public void setPayingStatus(int payingStatus) {
        this.payingStatus = payingStatus;
    }

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public String getStartData() {
        return startData;
    }

    public void setStartData(String startData) {
        this.startData = startData;
    }

    public String getEndData() {
        return endData;
    }

    public void setEndData(String endData) {
        this.endData = endData;
    }

    public String getGuideMobile() {
        return guideMobile;
    }

    public void setGuideMobile(String guideMobile) {
        this.guideMobile = guideMobile;
    }

    public String getGuideSureTime() {
        return guideSureTime;
    }

    public void setGuideSureTime(String guideSureTime) {
        this.guideSureTime = guideSureTime;
    }

    public String getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(String lastPayTime) {
        this.lastPayTime = lastPayTime;
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

    public int getBalanceStatus() {
        return balanceStatus;
    }

    public void setBalanceStatus(int balanceStatus) {
        this.balanceStatus = balanceStatus;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public float getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(float payPrice) {
        this.payPrice = payPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatusStr() {
        switch (payStatus) {
            case Constant.ORDER_PAY_WAIT:
                switch (payingStatus){
                    case Constant.ORDER_WAIT_PAY:
                        return "待付款";
                    case Constant.ORDER_WAIT_PAYING:
                        return "付款中";
                    default:
                        return "";
                }
            case Constant.ORDER_PAY_ALREADY:
                switch (orderStatus) {
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
                return "退款";
        }
        return "";
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public List<OrderDetailChildModel> getNjzChildOrderVOS() {
        return njzChildOrderVOS;
    }

    public void setNjzChildOrderVOS(List<OrderDetailChildModel> njzChildOrderVOS) {
        this.njzChildOrderVOS = njzChildOrderVOS;
    }

    public EvaluateTypeModel getEvaluateType() {
        EvaluateTypeModel evaluateTypeModel = new EvaluateTypeModel();
        for (int i = 0; i < njzChildOrderVOS.size(); i++) {
            OrderDetailChildModel childModel = njzChildOrderVOS.get(i);
            if (TextUtils.equals(childModel.getValue(), Constant.SERVICE_TYPE_SHORT_CUSTOM)) {
                evaluateTypeModel.setGuide(true);
                evaluateTypeModel.setCar(true);
                evaluateTypeModel.setTravel(true);
                evaluateTypeModel.setTrip(true);
                break;
            } else if (TextUtils.equals(childModel.getValue(), Constant.SERVICE_TYPE_SHORT_GUIDE)) {
                evaluateTypeModel.setGuide(true);
                evaluateTypeModel.setTravel(true);
            } else if (TextUtils.equals(childModel.getValue(), Constant.SERVICE_TYPE_SHORT_CAR)) {
                evaluateTypeModel.setGuide(true);
                evaluateTypeModel.setTravel(true);
                evaluateTypeModel.setCar(true);
            } else if (TextUtils.equals(childModel.getValue(), Constant.SERVICE_TYPE_SHORT_TICKET)
                    || TextUtils.equals(childModel.getValue(), Constant.SERVICE_TYPE_SHORT_HOTEL)) {
                evaluateTypeModel.setTrip(true);
            }
        }
        return evaluateTypeModel;
    }
}
