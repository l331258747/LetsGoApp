package com.njz.letsgoapp.util.rxbus.busEvent;

import com.njz.letsgoapp.bean.server.PriceCalendarChildModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/12/13
 * Function:
 */

public class PriceCalendarEvent {
    List<PriceCalendarChildModel> priceCalendarChildModels;

    public PriceCalendarEvent(List<PriceCalendarChildModel> priceCalendarChildModels) {
        this.priceCalendarChildModels = priceCalendarChildModels;
    }

    public List<PriceCalendarChildModel> getPriceCalendarChildModels() {
        return priceCalendarChildModels;
    }
}
