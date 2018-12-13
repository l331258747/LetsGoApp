package com.njz.letsgoapp.view.calendarDecorators;

/**
 * Created by LGQ
 * Time: 2018/12/11
 * Function:
 */

public class PriceModel {
    String time;
    String price;

    public PriceModel(String time, String price) {
        this.time = time;
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
