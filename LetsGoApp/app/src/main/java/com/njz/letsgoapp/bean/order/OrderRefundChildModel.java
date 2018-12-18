package com.njz.letsgoapp.bean.order;

import com.njz.letsgoapp.constant.Constant;

/**
 * Created by LGQ
 * Time: 2018/9/29
 * Function:
 */

public class OrderRefundChildModel {

    /**
     * defaultMoney : 0
     * titleImg : 11111111111
     * serveType : 26
     * unUseDay : 0
     * orderStatus : 0
     * refundMoney : 0
     * personNum : 0
     * title : 张家界峡谷大酒店
     * roomNum : 2
     * travelDate :
     * payPrice : 0
     * price : 128.0
     * ticketNum : 0
     * childOrderId : 0
     * dayNum : 2
     * useMoney : 0
     * value : ddjd
     * useDay : 0
     */

    private float defaultMoney;
    private String titleImg;
    private int serveType;
    private int unUseDay;
    private int orderStatus;
    private float refundMoney;
    private int personNum;
    private String title;
    private int roomNum;
    private String travelDate;
    private float payPrice;
    private float price;
    private int ticketNum;
    private int childOrderId;
    private int dayNum;
    private float useMoney;
    private float orderPrice;
    private String value;
    private int useDay;
    private int serveId;

    public String getServerName() {
        switch (value){
            case Constant.SERVER_TYPE_GUIDE:
                return "向导陪游";
            case Constant.SERVER_TYPE_FEATURE:
                return "tsty特色体验";
            case Constant.SERVER_TYPE_CUSTOM:
                return "私人定制";
            case Constant.SERVER_TYPE_HOTEL:
                return "代订酒店";
            case Constant.SERVER_TYPE_TICKET:
                return "代订门票";
            case Constant.SERVER_TYPE_CAR:
                return "接送机/站";
        }
        return "";
    }

    public int getServeId() {
        return serveId;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public float getDefaultMoney() {
        return defaultMoney;
    }

    public void setDefaultMoney(float defaultMoney) {
        this.defaultMoney = defaultMoney;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public float getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(float refundMoney) {
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

    public int getChildOrderId() {
        return childOrderId;
    }

    public void setChildOrderId(int childOrderId) {
        this.childOrderId = childOrderId;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public float getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(float useMoney) {
        this.useMoney = useMoney;
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
