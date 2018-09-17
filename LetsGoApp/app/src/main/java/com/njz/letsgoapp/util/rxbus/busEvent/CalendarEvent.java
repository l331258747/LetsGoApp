package com.njz.letsgoapp.util.rxbus.busEvent;

/**
 * Created by LGQ
 * Time: 2018/8/8
 * Function:
 */

public class CalendarEvent {
    String startTime;
    String endTime;
    String days;

    public CalendarEvent(String startTime, String endTime, String days) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDays() {
        return days;
    }

}
