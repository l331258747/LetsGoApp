package com.njz.letsgoapp.util.rxbus.busEvent;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class CityPickEvent {
    String city;

    public CityPickEvent(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
