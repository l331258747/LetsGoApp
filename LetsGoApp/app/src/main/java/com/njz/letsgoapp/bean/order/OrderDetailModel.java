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
    private String planDesignTime;
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
    private String startDate;
    private String endDate;
    private int personNum;
    private int planStatus;
    private List<OrderDetailChildModel> njzChildOrderVOS;
    private int payingStatus;
    private int serveId;
    private boolean havCar;
    private int children;
    private int adult;
    private String cancelReason;
    private String cancelExplain;
    private String cancelTime;

    private float typeMoney;

    public float getCouponPrice() {
        return typeMoney;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public String getCancelExplain() {
        return cancelExplain;
    }

    public int getChildren() {
        return children;
    }

    public int getAdult() {
        return adult;
    }

    public boolean isCustom() {
        if(njzChildOrderVOS !=null && njzChildOrderVOS.size()==1 && njzChildOrderVOS.get(0).getServeType() == Constant.SERVER_TYPE_CUSTOM_ID){
            return true;
        }
        return false;
    }

    public boolean isHavCar() {
        return havCar;
    }

    public String getPlanDesignTime() {
        return planDesignTime;
    }

    public String getPersonNum() {
        if(isCustom()){
            return adult+"成人"+children+"儿童";
        }
        return personNum + "";
    }

    public int getPlanStatus() {
        return planStatus;
    }

    public void setPlanDesignTime(String planDesignTime) {
        this.planDesignTime = planDesignTime;
    }

    public void setPlanStatus(int planStatus) {
        this.planStatus = planStatus;
    }

    public int getServeId() {
        return serveId;
    }

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
        if (TextUtils.equals(payType, "WxPay")) {
            return "微信支付";
        } else if (TextUtils.equals(payType, "AliPay")) {
            return "支付宝支付";
        }
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
        if(isCustom()){
            return orderPrice;
        }
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

    public String getOrderPriceStr() {
        if (payStatus == Constant.ORDER_PAY_WAIT
                && payingStatus == Constant.ORDER_WAIT_PAY
                && (planStatus == Constant.ORDER_PLAN_GUIDE_WAIT || planStatus == Constant.ORDER_PLAN_PLANING)) {
            return ("报价待确定");
        } else if(payStatus == Constant.ORDER_PAY_CANCEL){
            return ("" + orderPrice);
        } else if(isCustom()){
            return ("" + orderPrice);
        }else {
            return ("" + payPrice);
        }
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
                switch (payingStatus) {
                    case Constant.ORDER_WAIT_PAY:
                        switch (planStatus) {
                            case Constant.ORDER_PLAN_GUIDE_WAIT:
                                return "待确认";
                            case Constant.ORDER_PLAN_PLANING:
                                return "方案设计中";
                        }
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
                switch (reviewStatus) {
                    case Constant.ORDER_EVALUATE_NO:
                        return "未点评";
                    case Constant.ORDER_EVALUATE_YES:
                        return "已点评";
                    default:
                        return "";
                }
            case Constant.ORDER_PAY_REFUND:
                return "退款";
            case Constant.ORDER_PAY_CANCEL:
                return "已取消";
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

            if(childModel.getPayStatus() != Constant.ORDER_PAY_FINISH)
                continue;

            if (childModel.getServeType() == Constant.SERVER_TYPE_CUSTOM_ID) {
                evaluateTypeModel.setAttitude(true);
                evaluateTypeModel.setQuality(true);
                evaluateTypeModel.setScheduling(true);
                evaluateTypeModel.setCustom(true);
                break;
            } else if (childModel.getServeType() == Constant.SERVER_TYPE_GUIDE_ID
                    || childModel.getServeType() == Constant.SERVER_TYPE_FEATURE_ID) {
                evaluateTypeModel.setAttitude(true);
                evaluateTypeModel.setQuality(true);
                evaluateTypeModel.setScheduling(true);
                if (havCar)
                    evaluateTypeModel.setCarCondition(true);
            } else if (childModel.getServeType() == Constant.SERVER_TYPE_CAR_ID) {
                evaluateTypeModel.setAttitude(true);
                evaluateTypeModel.setQuality(true);
                evaluateTypeModel.setCarCondition(true);
            } else if (childModel.getServeType() == Constant.SERVER_TYPE_TICKET_ID
                    || childModel.getServeType() == Constant.SERVER_TYPE_HOTEL_ID) {
                evaluateTypeModel.setAttitude(true);
                evaluateTypeModel.setQuality(true);
            }
        }
        return evaluateTypeModel;
    }
}
