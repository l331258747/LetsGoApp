package com.njz.letsgoapp.bean.order;

import com.njz.letsgoapp.constant.Constant;

/**
 * Created by LGQ
 * Time: 2018/9/28
 * Function:
 */

public class OrderDetailChildModel {

    /**
     * defaultMoney : 0
     * titleImg : 1231
     * childOrderStatus : 0
     * orderId : 94
     * serveType : 0
     * unUseDay : 0
     * refundMoney : 0
     * personNum : 0
     * title : 长沙美女带你游
     * roomNum : 1
     * travelDate : 2018-9-28,2018-9-29
     * payPrice : 224.0
     * price : 112.0
     * ticketNum : 0
     * dayNum : 2
     * orderPrice : 224.0
     * id : 174
     * payStatus : 0
     * value : xdpy
     * useDay : 0
     */

    private int defaultMoney;
    private String titleImg;
    private int childOrderStatus;
    private int orderId;
    private int serveType;
    private int unUseDay;
    private int refundMoney;
    private int personNum;
    private String title;
    private int roomNum;
    private String travelDate;
    private float payPrice;
    private float price;
    private int ticketNum;
    private int dayNum;
    private float orderPrice;
    private int id;
    private int payStatus;
    private String value;
    private int useDay;
    private int payingStatus;
    private int serveId;
    private String location;
    private int serveNum;
    private int planStatus;

    public int getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(int planStatus) {
        this.planStatus = planStatus;
    }

    public int getServeNum() {
        return serveNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getTimeTitle(){
        switch (serveType){
            case Constant.SERVER_TYPE_GUIDE_ID:
                return "行程时间";
            case Constant.SERVER_TYPE_HOTEL_ID:
                return "入住时间";
            case Constant.SERVER_TYPE_TICKET_ID:
                return "日期";
            case Constant.SERVER_TYPE_CAR_ID:
                return "出发日期";
            case Constant.SERVER_TYPE_FEATURE_ID:
                return "出发日期";
            case Constant.SERVER_TYPE_CUSTOM_ID:
                return "行程时间";
        }
        return "";
    }

    public String getCountContent(){
        switch (serveType){
            case Constant.SERVER_TYPE_CUSTOM_ID:
                return serveNum + "";
            case Constant.SERVER_TYPE_HOTEL_ID:
                return serveNum + "间";
            case Constant.SERVER_TYPE_TICKET_ID:
                return serveNum + "张";
            case Constant.SERVER_TYPE_CAR_ID:
                return serveNum + "次";
            case Constant.SERVER_TYPE_FEATURE_ID:
                return serveNum + "次";
        }
        return "";
    }

    public String getServerName() {
        switch (serveType){
            case Constant.SERVER_TYPE_GUIDE_ID:
                return "向导陪游";
            case Constant.SERVER_TYPE_FEATURE_ID:
                return "特色体验";
            case Constant.SERVER_TYPE_CUSTOM_ID:
                return "私人定制";
            case Constant.SERVER_TYPE_HOTEL_ID:
                return "代订酒店";
            case Constant.SERVER_TYPE_TICKET_ID:
                return "代订门票";
            case Constant.SERVER_TYPE_CAR_ID:
                return "接送机/站";
        }
        return "";
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

    public int getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(int defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public int getChildOrderStatus() {
        return childOrderStatus;
    }

    public void setChildOrderStatus(int childOrderStatus) {
        this.childOrderStatus = childOrderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getServeType() {
        return serveType;
    }

    public void setServeType(int serveType) {
        this.serveType = serveType;
    }

    public int getUnUseDay() {
        return unUseDay;
    }

    public void setUnUseDay(int unUseDay) {
        this.unUseDay = unUseDay;
    }

    public int getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(int refundMoney) {
        this.refundMoney = refundMoney;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }

    public float getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(float payPrice) {
        this.payPrice = payPrice;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getUseDay() {
        return useDay;
    }

    public void setUseDay(int useDay) {
        this.useDay = useDay;
    }
}
