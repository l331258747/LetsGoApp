package com.njz.letsgoapp.bean.order;

import com.njz.letsgoapp.constant.Constant;

public class OrderChildModel {
    /**
     * roomNum : 2
     * payPrice : 0
     * price : 128.0
     * ticketNum : 0
     * dayNum : 2
     * orderPrice : 512.0
     * id : 159
     * personNum : 0
     * title : 张家界峡谷大酒店
     */

    private String titleImg;
    private int roomNum;
    private float payPrice;
    private float price;
    private int ticketNum;
    private int dayNum;
    private float orderPrice;
    private int id;
    private int personNum;
    private String title;
    private int serveType;
    private String value;
    private int payStatus;
    private int childOrderStatus;
    private int payingStatus;

    public String getServerName() {
        switch (serveType){
            case Constant.SERVER_TYPE_GUIDE_ID:
                return "向导陪游";
            case Constant.SERVER_TYPE_FEATURE_ID:
                return "tsty特色体验";
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

    public int getPayingStatus() {
        return payingStatus;
    }

    public void setPayingStatus(int payingStatus) {
        this.payingStatus = payingStatus;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getChildOrderStatus() {
        return childOrderStatus;
    }

    public void setChildOrderStatus(int childOrderStatus) {
        this.childOrderStatus = childOrderStatus;
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

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getServeType() {
        return serveType;
    }

    public void setServeType(int serveType) {
        this.serveType = serveType;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}