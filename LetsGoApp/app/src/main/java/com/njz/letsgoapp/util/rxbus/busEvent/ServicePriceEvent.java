package com.njz.letsgoapp.util.rxbus.busEvent;

/**
 * Created by LGQ
 * Time: 2018/9/14
 * Function:
 */

public class ServicePriceEvent {

    private float price;

    public ServicePriceEvent() {
    }

    public ServicePriceEvent(float price) {
        this.price = price;
    }

    public float getPrice() {
        return price;
    }
}
