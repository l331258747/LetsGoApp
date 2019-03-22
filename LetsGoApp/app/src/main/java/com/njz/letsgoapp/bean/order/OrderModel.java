package com.njz.letsgoapp.bean.order;

import android.text.TextUtils;

import com.njz.letsgoapp.constant.Constant;

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

    private int guideId;
    private String orderNo;
    private float payPrice;
    private int orderStatus;
    private int reviewStatus;
    private String location;
    private float orderPrice;
    private int payingStatus;
    private int id;
    private int payStatus;
    private String guideName;
    private String mobile;
    private String name;
    private String guideMobile;
    private String lastPayTime;
    private int planStatus;
    private boolean havCar;
    private List<OrderChildModel> njzChildOrderListVOS;

    private int children;
    private int adult;
    private int personNum;
    private String SpecialRequire;

    public String getSpecialRequire() {
        return SpecialRequire;
    }

    public String getPersonNum() {
//        if(isCustom()){
//            return adult+"成人"+children+"儿童";
//        }
        return personNum + "";
    }

    public boolean isCustom() {
        if(njzChildOrderListVOS !=null && njzChildOrderListVOS.size()==1 && njzChildOrderListVOS.get(0).getServeType() == Constant.SERVER_TYPE_CUSTOM_ID){
            return true;
        }
        return false;
    }

    public String getLastPayTime() {
        return lastPayTime;
    }

    public boolean isHavCar() {
        return havCar;
    }

    public int getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(int planStatus) {
        this.planStatus = planStatus;
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
//        if(payStatus == Constant.ORDER_PAY_WAIT && isCustom()){
//            return orderPrice;
//        }
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


    public EvaluateTypeModel getEvaluateType() {
        EvaluateTypeModel evaluateTypeModel = new EvaluateTypeModel();
        for (int i = 0; i < njzChildOrderListVOS.size(); i++) {
            OrderChildModel childModel = njzChildOrderListVOS.get(i);

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
                if(havCar)
                    evaluateTypeModel.setCarCondition(true);
            } else if (childModel.getServeType() == Constant.SERVER_TYPE_CAR_ID) {
                evaluateTypeModel.setAttitude(true);
                evaluateTypeModel.setQuality(true);
                evaluateTypeModel.setCarCondition(true);
            } else if(childModel.getServeType() == Constant.SERVER_TYPE_TICKET_ID
                    || childModel.getServeType() == Constant.SERVER_TYPE_HOTEL_ID){
                evaluateTypeModel.setAttitude(true);
                evaluateTypeModel.setQuality(true);
            }
        }
        return evaluateTypeModel;
    }

}
