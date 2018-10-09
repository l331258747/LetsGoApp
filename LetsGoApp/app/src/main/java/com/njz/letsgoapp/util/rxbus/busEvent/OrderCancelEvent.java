package com.njz.letsgoapp.util.rxbus.busEvent;

/**
 * Created by LGQ
 * Time: 2018/10/9
 * Function:
 */

public class OrderCancelEvent {

    public int isMainly;

    public OrderCancelEvent(int isMainly) {
        this.isMainly = isMainly;
    }

    public int getIsMainly() {
        return isMainly;
    }

}
