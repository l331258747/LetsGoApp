package com.njz.letsgoapp.bean.send;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/9/18
 * Function:
 */

public class SendOrderModel {

    int guideId;
    String mobile;
    String name;
    String specialRequire;
    String location;
    List<SendChildOrderModel> childOrders;
    float earlyOrderPrice;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGuideId() {
        return guideId;
    }

    public void setGuideId(int guideId) {
        this.guideId = guideId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getSpecialRequire() {
        return specialRequire;
    }

    public void setSpecialRequire(String specialRequire) {
        this.specialRequire = specialRequire;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<SendChildOrderModel> getChildOrders() {
        return childOrders;
    }

    public void setChildOrders(List<SendChildOrderModel> childOrders) {
        this.childOrders = childOrders;
    }

    public float getEarlyOrderPrice() {
        return earlyOrderPrice;
    }

    public void setEarlyOrderPrice(float earlyOrderPrice) {
        this.earlyOrderPrice = earlyOrderPrice;
    }
}
