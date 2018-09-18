package com.njz.letsgoapp.bean.send;

/**
 * Created by LGQ
 * Time: 2018/9/18
 * Function:
 */

public class SendChildOrderModel {

    int serveId;
    String title;
    float price;
    int roomNum;
    int dayNum;
    float earlyOrderPrice;
    String travelDate;


    public int getServeId() {
        return serveId;
    }

    public void setServeId(int serveId) {
        this.serveId = serveId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public float getEarlyOrderPrice() {
        return earlyOrderPrice;
    }

    public void setEarlyOrderPrice(float earlyOrderPrice) {
        this.earlyOrderPrice = earlyOrderPrice;
    }

    public String getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(String travelDate) {
        this.travelDate = travelDate;
    }
}
