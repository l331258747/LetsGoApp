package com.njz.letsgoapp.util.rxbus.busEvent;

/**
 * Created by LGQ
 * Time: 2019/2/27
 * Function:
 */

public class OrderCouponEvent {

    int id;
    float price;

    public OrderCouponEvent(int id,float price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }
}
